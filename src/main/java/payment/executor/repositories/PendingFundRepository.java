package payment.executor.repositories;

import org.springframework.data.repository.CrudRepository;
import payment.executor.entities.PendingFunds;

import java.util.UUID;

public interface PendingFundRepository extends CrudRepository<PendingFunds, Long> {
    PendingFunds findByTransactionId(UUID transactionId);
}
