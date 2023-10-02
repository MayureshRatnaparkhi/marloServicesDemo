package ml.marlo.services.account.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleValidatorImpl.class)
public @interface DoubleValidator {
	String message() default "minimum balance required to open account";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}