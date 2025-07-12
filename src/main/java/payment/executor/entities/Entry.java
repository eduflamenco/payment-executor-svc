package payment.executor.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", referencedColumnName = "account_number", nullable = false)
    private Account account;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "amount", nullable = false)
    private double amount = 0.00;

    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Override
    public String toString() {
        return "Account{" +
                "entryId =" + id +
                ", accountNumber='" + account + '\'' +
                ", type ='" + type + '\'' +
                ", amount =" + amount +
                ", transactionId =" + transactionId +
                ", createdDate =" + createdDate +
                '}';
    }
}
