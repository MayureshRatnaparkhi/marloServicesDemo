package ml.marlo.services.transaction.constant;

import lombok.Getter;
import java.util.Optional;
@Getter
public enum TransactionType {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");
    TransactionType(String value) {
        this.value = value;
    }

    private String value;

    public static Optional<TransactionType> getEnum(String value) {
        for (TransactionType v : values())
            if (v.getValue().equalsIgnoreCase(value)) return Optional.of(v);
        return Optional.empty();
    }

}