package ml.marlo.services.account.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.marlo.services.account.validator.AccountTypeValidator;
import ml.marlo.services.account.validator.BankNameValidator;
import ml.marlo.services.account.validator.DoubleValidator;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @BankNameValidator
    private String bankName;

    @NotEmpty(message = "Account holder name is required")
    @NotNull(message = "Account holder name is required")
    private String name;

    @AccountTypeValidator
    private String accountType;

    @DoubleValidator
    private double amount;
}
