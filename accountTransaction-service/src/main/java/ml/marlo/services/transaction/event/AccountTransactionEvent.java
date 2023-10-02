package ml.marlo.services.transaction.event;

import lombok.*;
import ml.marlo.services.transaction.response.AccountResponse;

@Getter
@Setter
@Data
@ToString
@Builder
public class AccountTransactionEvent {

	private String type;

	private AccountResponse accountStatus;

}
