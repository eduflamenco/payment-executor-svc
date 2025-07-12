package payment.executor.validate;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import payment.executor.contracts.request.PaymentTransaction;

@Component
public class AmountValidator extends Validator {

    private Environment env;

    public AmountValidator(Environment env) {
        this.env = env;
    }

    @Override
    public ValidatorResult validate(PaymentTransaction payReq) {
        if(payReq.paymentAmount() > env.getProperty("application.properties.transaction.max-amount", Integer.class)){
            return new ValidatorResult(false, "El monto de la transaccion excede el limite permitido");
        }

        return checkNext(payReq);
    }
}
