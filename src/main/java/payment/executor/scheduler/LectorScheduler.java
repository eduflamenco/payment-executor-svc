package payment.executor.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LectorScheduler {

    /*private final MakeTransaction makeTransaction;

    public LectorScheduler(MakeTransaction makeTransaction) {
        this.makeTransaction = makeTransaction;
    }

    @Scheduled(*//*initialDelay = 10000, *//*fixedDelay = 30000) // cada 30 segundos
    public void leerPeriodicamente() {
        makeTransaction.makeTransaction();
    }*/
}
