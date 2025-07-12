package payment.executor.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import payment.executor.confirmation.PayConfirmation;

@Component
@EnableScheduling
public class LectorScheduler {

    private final PayConfirmation payConfirmation;

    public LectorScheduler(PayConfirmation payConfirmation) {
        this.payConfirmation = payConfirmation;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 30000) // cada 30 segundos
    public void readTransactions() {
        payConfirmation.processTransactions();
    }
}
