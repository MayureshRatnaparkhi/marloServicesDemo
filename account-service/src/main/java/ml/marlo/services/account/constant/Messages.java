package ml.marlo.services.account.constant;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Messages {

    INITIAL_DEPOSIT_AMOUNT("{DEPOSIT_AMT} minimum balance required to open account"),
    DEPOSIT_AMT("{DEPOSIT_AMT}");

    Messages(String value) {
        this.value = value;
    }

    private String value;

    public static Optional<Messages> getEnum(String value) {
        for (Messages v : values())
            if (v.getValue().equalsIgnoreCase(value)) return Optional.of(v);
        return Optional.empty();
    }

}