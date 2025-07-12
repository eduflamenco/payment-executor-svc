package payment.executor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.executor.contracts.request.PaymentTransaction;
import payment.executor.contracts.response.PaymentResponse;
import payment.executor.validate.ExecuteValidation;

@RestController
@RequestMapping("/payment")
public class ValidatePayment {

    private final ExecuteValidation executeValidation;

    public ValidatePayment(ExecuteValidation executeValidation) {
        this.executeValidation = executeValidation;
    }

    @PostMapping("/validate")
    public ResponseEntity<PaymentResponse> validatePayment(@RequestBody PaymentTransaction  payReq){
        var resp = executeValidation.executeValidation(payReq);
        return ResponseEntity.ok(resp);
    }
}
