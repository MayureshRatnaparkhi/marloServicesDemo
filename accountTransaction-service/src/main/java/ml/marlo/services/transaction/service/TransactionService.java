package ml.marlo.services.transaction.service;

import ml.marlo.services.transaction.constant.TransactionType;
import ml.marlo.services.transaction.event.AccountTransactionEvent;
import ml.marlo.services.transaction.exceptions.RestException;
import ml.marlo.services.transaction.helper.TransactionHelper;
import ml.marlo.services.transaction.repository.TransactionRepository;
import ml.marlo.services.transaction.request.AccountTransactionRequest;
import ml.marlo.services.transaction.response.APIResponse;
import ml.marlo.services.transaction.response.APIResponseMessage;
import ml.marlo.services.transaction.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.core.KafkaTemplate;
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KafkaTemplate<String, AccountTransactionEvent> kafkaTemplate;

    public Mono<APIResponse> transaction(
            AccountTransactionRequest accountTransaction,
            String transactionType) {
        Optional<TransactionType> type = TransactionType.getEnum(transactionType);
        if (!type.isPresent()) {
            return Mono.error(new RestException(TransactionHelper.INITIAL_TRANSACTION_TYPE.getValue(), HttpStatus.BAD_REQUEST));
        }
        if (transactionType.equals(TransactionType.DEBIT.getValue())) {
            return debitTransaction(accountTransaction);
        }
        return creditTransaction(accountTransaction);
    }

    private Mono<APIResponse> debitTransaction(
            AccountTransactionRequest accountTransaction) {

        return webClientBuilder.build().get().uri(TransactionHelper.ACCOUNT_URL.getValue(),
                        accountTransaction.getAccountId()).retrieve().bodyToMono(AccountResponse.class)
                .switchIfEmpty(Mono.error(new RestException(TransactionHelper.INVALID_ACCOUNT_ID.getValue(), HttpStatus.BAD_REQUEST)))
                .flatMap(accountResponse -> {
                    return TransactionHelper.validateBalance(accountResponse, accountTransaction).flatMap(v -> {
                        return repository.sumOfDebitAmountByAccountId(TransactionHelper.getCurrentDate(),
                                        String.valueOf(accountTransaction.getAccountId()))
                                .switchIfEmpty(Mono.defer(() -> Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
                                    return 0;
                                }))))
                                .flatMap(debitAmt -> {
                                    if ((debitAmt + accountTransaction.getAmount()) > accountResponse.getWithdrawalLimitPerDay()) {
                                        return Mono.error(new RestException(TransactionHelper.WITHDRAWAL_LIMIT
                                                .getValue().replace(TransactionHelper.LIMIT.getValue(),
                                                        String.valueOf(accountResponse.getWithdrawalLimitPerDay())), HttpStatus.BAD_REQUEST));
                                    }

                                    return repository.save(TransactionHelper.debitAccountTransactionRequestPayload.apply(accountTransaction))
                                            .flatMap(debitResponse -> {

                                                return webClientBuilder.build().put()
                                                        .uri(TransactionHelper.ACCOUNT_TRANSACTION_URL.getValue(),
                                                                accountTransaction.getAccountId(), accountTransaction.getAmount(),
                                                                TransactionType.DEBIT.getValue())
                                                        .headers((httpHeaders) -> {
                                                            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                                        })
                                                        .retrieve().bodyToMono(AccountResponse.class)
                                                        .flatMap(availableBalance -> {
                                                            sendMessage(availableBalance,TransactionHelper.AMOUNT_DEBITED.getValue());
                                                            return Mono.just(APIResponseMessage.builder()
                                                                    .data(availableBalance)
                                                                    .statusCode(HttpStatus.OK.value())
                                                                    .message(HttpStatus.OK.name()).build());
                                                        });


                                            });

                                });
                    });


                });
    }

    private Mono<APIResponse> creditTransaction(
            AccountTransactionRequest accountTransaction) {

        return repository.save(TransactionHelper.creditAccountTransactionRequestPayload.apply(accountTransaction))
                .flatMap(creditResponse -> {
                    return webClientBuilder.build().put()
                            .uri(TransactionHelper.ACCOUNT_TRANSACTION_URL.getValue(),
                                    accountTransaction.getAccountId(), accountTransaction.getAmount(),
                                    TransactionType.CREDIT.getValue())
                            .headers((httpHeaders) -> {
                                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                            })
                            .retrieve().bodyToMono(AccountResponse.class)
                            .flatMap(availableBalance -> {
                                sendMessage(availableBalance,TransactionHelper.AMOUNT_CREDITED.getValue());
                                return Mono.just(APIResponseMessage.builder()
                                        .data(availableBalance)
                                        .statusCode(HttpStatus.OK.value())
                                        .message(HttpStatus.OK.name()).build());
                            });

                });
    }
    private void sendMessage(AccountResponse availableBalance , String transactionType)
    {
        kafkaTemplate.send(TransactionHelper.NEW_TRANSACTION.getValue(), AccountTransactionEvent.builder()
                .type(transactionType)
                .accountStatus(availableBalance)
                .build());
    }

}
