package payment.executor.validate;

import org.springframework.stereotype.Component;
import payment.executor.contracts.request.PaymentTransaction;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateValidator extends Validator{

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");
    @Override
    public ValidatorResult validate(PaymentTransaction payReq) {

        try {
            YearMonth fechaVigencia = YearMonth.parse(payReq.creditCardDetails().expirationDate(), FORMATTER);
            YearMonth fechaActual = YearMonth.now();
            if (fechaVigencia.isBefore(fechaActual)) {
                return new ValidatorResult(false, "La tarjeta esta vencida.");
            }
        }catch (DateTimeParseException e) {
            System.out.println("Fecha inválida: " + e.getMessage());
            return new ValidatorResult(false, "Fecha inválida: " + e.getMessage());
        }

        return checkNext(payReq);
    }
}
