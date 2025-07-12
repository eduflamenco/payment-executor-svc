package payment.executor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import payment.executor.entities.Entry;

public interface EntryRepository  extends JpaRepository<Entry, Long> {
}
