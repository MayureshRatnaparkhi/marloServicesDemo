package ml.marlo.services.transaction.event;

import lombok.*;
import ml.marlo.services.transaction.response.AccountResponse;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionEvent {

	private String type;

	private AccountResponse accountStatus;

}
