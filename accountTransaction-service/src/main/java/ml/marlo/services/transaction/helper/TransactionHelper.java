package ml.marlo.services.transaction.helper;

import lombok.Getter;
import ml.marlo.services.transaction.constant.TransactionType;
import ml.marlo.services.transaction.exceptions.RestException;
import ml.marlo.services.transaction.model.AccountTransaction;
import ml.marlo.services.transaction.request.AccountTransactionRequest;
import ml.marlo.services.transaction.response.AccountResponse;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

@Getter
public enum TransactionHelper {

    ACCOUNT_URL("http://account-service/account/{customer}"),
    ACCOUNT_TRANSACTION_URL("http://account-service/account/transaction/{customer}/{creditAmount}/{transactionType}"),
    INITIAL_TRANSACTION_TYPE("Invalid Transaction Type , It should be DEBIT or CREDIT"),
    WITHDRAWAL_LIMIT("You can't withdrawl more than {LIMIT} amount"),
    LIMIT("{LIMIT}"),
    INVALID_ACCOUNT_ID("Invalid account id"),
    INSUFFICIENT_FUND("insufficient funds only {CURRENT_BAL} are balanced"),
    CURRENT_BAL("{CURRENT_BAL}"),
    AMOUNT_DEBITED("AMOUNT_DEBITED"),
    AMOUNT_CREDITED("MOUNT_CREDITED"),
    NEW_TRANSACTION("new-transaction");

    TransactionHelper(String value) {
        this.value = value;
    }

    private String value;

    public static Optional<TransactionHelper> getEnum(String value) {
        for (TransactionHelper v : values())
            if (v.getValue().equalsIgnoreCase(value)) return Optional.of(v);
        return Optional.empty();
    }

    public static String getCurrentDate()
    {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String transactionDate = dateObj.format(formatter);
        return transactionDate;
    }
    public static Function<AccountTransactionRequest, AccountTransaction> debitAccountTransactionRequestPayload=(accountTransactionRequest)->{

        return AccountTransaction.builder()
                .accountId(accountTransactionRequest.getAccountId())
                .trannsactionType(TransactionType.DEBIT.getValue())
                .debitAmount(accountTransactionRequest.getAmount())
                .build();
    };

    public static Function<AccountTransactionRequest,AccountTransaction> creditAccountTransactionRequestPayload=(accountTransactionRequest)->{

        return AccountTransaction.builder()
                .accountId(accountTransactionRequest.getAccountId())
                .trannsactionType(TransactionType.CREDIT.getValue())
                .creditAmount(accountTransactionRequest.getAmount())
                .build();
    };

    public static Mono<String> validateBalance(AccountResponse accountResponse, AccountTransactionRequest accountTransaction)
    {
        if ( (accountResponse.getBalanceAmount()-accountTransaction.getAmount())<0  || (accountResponse.getBalanceAmount()-accountTransaction.getAmount())==0)
        {
           throw new RestException(TransactionHelper.INSUFFICIENT_FUND
                    .getValue().replace(TransactionHelper.CURRENT_BAL.getValue(),
                            String.valueOf(accountResponse.getBalanceAmount())), HttpStatus.BAD_REQUEST);
        }
        if (accountTransaction.getAmount()>accountResponse.getBalanceAmount())
        {
            throw new RestException(TransactionHelper.INSUFFICIENT_FUND
                    .getValue().replace(TransactionHelper.CURRENT_BAL.getValue(),
                            String.valueOf(accountResponse.getBalanceAmount())), HttpStatus.BAD_REQUEST);
        }
        return Mono.just("validate");
    }
}