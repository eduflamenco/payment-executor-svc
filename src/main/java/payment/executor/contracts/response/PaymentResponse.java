package payment.executor.contracts.response;

import java.util.Objects;

public record PaymentResponse(
        String status,
        String message,
        String transactionId,
        String bill_number,
        double paymentTotal
) {
    public PaymentResponse {
        Objects.requireNonNull(status, "Status cannot be null");
        Objects.requireNonNull(message, "Message cannot be null");

        if (!status.equals("success") && !status.equals("error")) {
            throw new IllegalArgumentException("Status must be either 'success' or 'error'");
        }

    }

    // Helper method to check if the payment was successful
    public boolean isSuccess() {
        return "success".equals(status);
    }

    // Helper method to get formatted payment total
    public String getFormattedPaymentTotal() {
        return String.format("$%,.2f", paymentTotal);
    }
}
