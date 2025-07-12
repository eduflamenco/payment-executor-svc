package payment.executor.validate;

import org.springframework.stereotype.Component;
import payment.executor.contracts.validate.request.PaymentTransaction;
import payment.executor.contracts.validate.response.PaymentResponse;

@Component
public class ExecuteValidation {

    private Validator validator;

    public ExecuteValidation(AccountValidator accountValidator,  AmountValidator amountValidator, DateValidator dateValidator) {

        this.validator = Validator.linkValidator(dateValidator, amountValidator, accountValidator);
    }

    public PaymentResponse executeValidation(PaymentTransaction payReq){
        ValidatorResult result = null;
        try {
             result = validator.validate(payReq);
            if(!result.isValid()){
                return new PaymentResponse("error", result.getMessage(), "", "", 0);
            }
        }catch (Exception e){
            System.out.println("Exception occured" + e.getMessage());
            return new PaymentResponse("error", e.getMessage(), "", "", 0);
        }
        return  new PaymentResponse("success", result.getMessage(), result.getTransactionId(), result.getBillNumber(), result.getAmount());
    }

}
