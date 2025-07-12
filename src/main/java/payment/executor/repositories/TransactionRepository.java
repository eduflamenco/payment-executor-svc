package payment.executor.repositories;

import org.springframework.data.repository.CrudRepository;
import payment.executor.entities.Transaction;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

}
