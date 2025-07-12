package payment.executor.confirmation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import payment.executor.contracts.confirmation.TransactionMessage;
import payment.executor.repositories.ExecuteTransactions;

@Component
public class PayConfirmation {

    private final MessageReceiver messageReceiver;
    private final ExecuteTransactions executeTransaction;
    private final ObjectMapper objectMapper;

    public PayConfirmation(MessageReceiver messageReceiver, ObjectMapper objectMapper, ExecuteTransactions executeTransaction) {
        this.messageReceiver = messageReceiver;
        this.objectMapper = objectMapper;
        this.executeTransaction = executeTransaction;
    }

    public void processTransactions(){
        TransactionMessage transaction;
        var received = messageReceiver.receiveMessage();
        if(!received.isEmpty()) {
            try {
                for (var message : received) {
                    transaction = objectMapper.readValue(message, TransactionMessage.class);
                    System.out.println("Mensaje leido: "+ transaction);
                    String result = executeTransaction.execute(transaction);
                    System.out.println(result);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
