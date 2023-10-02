
package ml.marlo.services.account.repository;

import ml.marlo.services.account.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {

}

