package payment.executor.contracts.validate.request;

import java.util.Objects;

public record CreditCardDetails(
        String franchise,
        String cardNumber,
        String expirationDate
) {
    public CreditCardDetails {
        Objects.requireNonNull(franchise, "Franchise cannot be null");
        Objects.requireNonNull(cardNumber, "Card number cannot be null");
        Objects.requireNonNull(expirationDate, "Expiration date cannot be null");

        if (!cardNumber.matches("\\d{13,19}")) {
            throw new IllegalArgumentException("Invalid credit card number");
        }

        if (!expirationDate.matches("(0[1-9]|1[0-2])-\\d{4}")) {
            throw new IllegalArgumentException("Expiration date must be in MM-YYYY format");
        }
    }

    public String getMaskedCardNumber() {
        if (cardNumber.length() < 4) return "****";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}
