package ml.marlo.services.account.configuration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    private int bankCode;
    private String bankName;
    private double withdrawalLimitPerDay;
    private double initialDepositRequiredToOpenAccount;
}
