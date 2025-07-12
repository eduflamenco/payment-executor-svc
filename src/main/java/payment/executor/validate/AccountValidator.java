package payment.executor.validate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import payment.executor.contracts.request.PaymentTransaction;
import payment.executor.entities.Account;
import payment.executor.repositories.AccountRepository;

@Component
public class AccountValidator extends Validator {

    private AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public ValidatorResult validate(PaymentTransaction payReq) {
        Account account = accountRepository.findByAccountNumberForUpdate(payReq.creditCardDetails().cardNumber());
        if (account == null) {
            return new  ValidatorResult(false, "Numero de tarjeta invalido");
        }
        if (account.getBalance() - payReq.paymentAmount() < 0){
            return new  ValidatorResult(false, "Fondos insuficientes");
        }

        if (!account.getClient().getUsername().equals(payReq.customerId())){
            return new  ValidatorResult(false, "Error en validacion de Id del cliente");
        }

        return checkNext(payReq);
    }
}
