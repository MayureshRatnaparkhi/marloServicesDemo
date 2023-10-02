package ml.marlo.services.account.service;

import ml.marlo.services.account.configuration.Bank;
import ml.marlo.services.account.configuration.BankProperties;
import ml.marlo.services.account.constant.TransactionType;
import ml.marlo.services.account.helper.BankHelper;
import ml.marlo.services.account.repository.AccountRepository;
import ml.marlo.services.account.request.AccountRequest;
import ml.marlo.services.account.response.APIResponse;
import ml.marlo.services.account.response.APIResponseMessage;
import ml.marlo.services.account.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private BankProperties bankProperties;

    public Mono<AccountResponse> getAccount(String id) {
        return repository.findById(id).flatMap(resp -> {
            Optional<Bank> bank = BankHelper.getBank.apply(resp.getBankId(), bankProperties);
            if (bank.isPresent()) {
                return Mono
                        .just(AccountResponse.builder()
                                .accountId(resp.getId())
                                .balanceAmount(resp.getBalanceAmount())
                                .withdrawalLimitPerDay(bank.get().getWithdrawalLimitPerDay())
                                .build());
            }
            return Mono.empty();
        });
    }

    public Mono<AccountResponse> accountTransaction(String id, String transactionAmount, String transactionType) {
        return repository.findById(id).flatMap(resp -> {
            Optional<Bank> bank = BankHelper.getBank.apply(resp.getBankId(), bankProperties);

            if (transactionType.equals(TransactionType.DEBIT.getValue())) {
                double balanceAmt = (resp.getBalanceAmount() - Double.valueOf(transactionAmount));
                resp.setBalanceAmount(balanceAmt);
            } else {
                double balanceAmt = (resp.getBalanceAmount() + Double.valueOf(transactionAmount));
                resp.setBalanceAmount(balanceAmt);
            }
            return repository.save(resp).flatMap(updatedAccount -> {
                return Mono
                        .just(AccountResponse.builder()
                                .accountId(resp.getId())
                                .balanceAmount(resp.getBalanceAmount())
                                .withdrawalLimitPerDay(bank.get().getWithdrawalLimitPerDay())
                                .build());

            });

        });
    }

    public Mono<APIResponse> createAccount(AccountRequest accountRequest) {

        Optional<Bank> bank = BankHelper.validateBank.apply(accountRequest.getBankName(), bankProperties);
        BankHelper.validateMinimumBalance(accountRequest, bank.get());
        return repository.save(BankHelper.accountPayload.apply(bank.get(), accountRequest))
                .flatMap(resp -> {
                    return Mono
                            .just(APIResponseMessage.builder().data(AccountResponse.builder()
                                            .accountId(resp.getId())
                                            .withdrawalLimitPerDay(bank.get().getWithdrawalLimitPerDay())
                                            .build())
                                    .statusCode(HttpStatus.OK.value())
                                    .message(HttpStatus.OK.name()).build());
                });
    }

}
