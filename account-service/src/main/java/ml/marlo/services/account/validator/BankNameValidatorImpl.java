package ml.marlo.services.account.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.marlo.services.account.configuration.Bank;
import ml.marlo.services.account.configuration.BankProperties;
import ml.marlo.services.account.helper.BankHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankNameValidatorImpl implements ConstraintValidator<BankNameValidator, String> {
    @Autowired
    private BankProperties bankProperties;

    @Override
    public void initialize(BankNameValidator parameters) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Bank> bank = BankHelper.validateBank.apply(value, bankProperties);
        return bank.isPresent();
    }
}