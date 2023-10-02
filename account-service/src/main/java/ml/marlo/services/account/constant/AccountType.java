package ml.marlo.services.account.constant;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum AccountType {

    FD("FD"),
    SAVING("SAVING"),
    SALARY("SALARY");
    AccountType(String value) {
        this.value = value;
    }

    private String value;

    public static Optional<AccountType> getEnum(String value) {
        for (AccountType v : values())
            if (v.getValue().equalsIgnoreCase(value)) return Optional.of(v);
        return Optional.empty();
    }

}