package payment.executor.validate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class ValidatorResult {
    private boolean valid;
    private String message;

    public ValidatorResult(boolean valid) {
        this.valid = valid;
    }

    public ValidatorResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
