package ml.marlo.services.transaction.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("accountTransaction")
public class AccountTransaction {

	@Id
	@Column("id")
	private String id;

	@Column("accountId")
	private int accountId;

	@Column("trannsactionType")
	private String trannsactionType;

	@Column("debitAmount")
	private double debitAmount;

	@Column("creditAmount")
	private double creditAmount;

	@Column("transactionDate")
	private Date transactionDate;
}
