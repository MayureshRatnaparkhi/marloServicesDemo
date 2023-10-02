package ml.marlo.services.account.helper;

import ml.marlo.services.account.configuration.Bank;
import ml.marlo.services.account.configuration.BankProperties;
import ml.marlo.services.account.model.Account;
import ml.marlo.services.account.request.AccountRequest;
import ml.marlo.services.account.constant.Messages;
import ml.marlo.services.account.exceptions.RestException;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.BiFunction;

public interface BankHelper {

    BiFunction<String, BankProperties,Optional<Bank> >  validateBank=(bankName, bankProperties)->{

        return bankProperties.getBanks().stream().filter(b->b.getBankName().equals(bankName)).findFirst();
    };
    BiFunction<Integer, BankProperties,Optional<Bank> > getBank=(bacnkCode, bankProperties)->{

        return bankProperties.getBanks().stream().filter(b->b.getBankCode()==bacnkCode).findFirst();
    };
    BiFunction<Bank, AccountRequest, Account> accountPayload=(bank, accountRequest)->{

        return Account.builder()
                .bankId(bank.getBankCode())
                .name(accountRequest.getName())
                .accountType(accountRequest.getAccountType())
                .balanceAmount(accountRequest.getAmount())
                .build();
    };

    public static void validateMinimumBalance(AccountRequest accountRequest, Bank bank)
    {
        if (accountRequest.getAmount() < bank.getInitialDepositRequiredToOpenAccount())
        {
           throw new RestException(Messages.INITIAL_DEPOSIT_AMOUNT.getValue()
                    .replace(Messages.DEPOSIT_AMT.getValue(),
                            String.valueOf(bank.getInitialDepositRequiredToOpenAccount())),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
