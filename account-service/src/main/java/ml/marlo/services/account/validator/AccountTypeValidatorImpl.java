package ml.marlo.services.account.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ml.marlo.services.account.constant.AccountType;

public class AccountTypeValidatorImpl implements ConstraintValidator<AccountTypeValidator, String> {

    @Override
    public void initialize(AccountTypeValidator parameters) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return AccountType.getEnum(value).isPresent();
    }
}
