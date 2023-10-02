
package ml.marlo.services.account.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Account {
	@Id
	@Column("id")
	private int id;

	@Column("bankId")
	private int bankId;

	@Column("name")
	private String name;

	@Column("accountType")
	private String accountType;

	@Column("balanceAmount")
	private double balanceAmount;


}

