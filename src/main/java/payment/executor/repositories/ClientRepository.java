package payment.executor.repositories;

import org.springframework.data.repository.CrudRepository;
import payment.executor.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
