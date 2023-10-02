package ml.marlo.services.transaction.request;

import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionRequest {
	private int accountId;
	private double amount;
}
