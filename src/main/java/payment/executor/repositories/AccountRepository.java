package payment.executor.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import payment.executor.entities.Account;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, Long> {


    Account findByAccountNumber(String accountId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Account findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);
}
