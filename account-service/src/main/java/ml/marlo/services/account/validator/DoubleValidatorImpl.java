package ml.marlo.services.account.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DoubleValidatorImpl implements ConstraintValidator<DoubleValidator, Double> {

	@Override
	public void initialize(DoubleValidator parameters) {
	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {

		return value>0;
	}
}
