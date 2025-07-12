package payment.executor.contracts.request;

import java.util.Objects;

public record PaymentTransaction(
        String customerId,
        double paymentAmount,
        PaymentMethod paymentMethod,
        CreditCardDetails creditCardDetails
) {
    public PaymentTransaction {
        Objects.requireNonNull(customerId, "Customer ID cannot be null");
        if (paymentAmount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        Objects.requireNonNull(paymentMethod, "Payment method cannot be null");

        // Only require credit card details if payment method is credit card
        if (paymentMethod == PaymentMethod.CREDIT_CARD) {
            Objects.requireNonNull(creditCardDetails, "Credit card details are required for credit card payments");
        }
    }
}

