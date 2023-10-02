package ml.marlo.services.account.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private int accountId;
    private double withdrawalLimitPerDay;
    private double balanceAmount;
}
