package payment.executor.validate;


import payment.executor.contracts.request.PaymentTransaction;

public abstract class Validator {
    private Validator next;

    public static Validator linkValidator(Validator firts, Validator... chain){
        Validator head = firts;
        for(Validator nextInChain : chain){
            head.next = nextInChain;
            head = nextInChain;
        }
        return firts;
    }

    public abstract ValidatorResult validate(PaymentTransaction payReq);


    protected ValidatorResult checkNext(PaymentTransaction payReq) {
        if (next == null) {
            return new ValidatorResult(true, "Pago procesado con éxito.");
        }
        return next.validate(payReq);
    }

}
