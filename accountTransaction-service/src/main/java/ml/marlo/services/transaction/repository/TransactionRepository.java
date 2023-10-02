package ml.marlo.services.transaction.repository;

import ml.marlo.services.transaction.model.AccountTransaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<AccountTransaction, String> {

    @Query("select sum(debitAmount) from accountTransaction  WHERE DATE(transactionDate) = :transactionDate  and accountId=:accountId")
    Mono<Integer> sumOfDebitAmountByAccountId(@Param("transactionDate") String transactionDate,
                               @Param("accountId") String accountId);


}
