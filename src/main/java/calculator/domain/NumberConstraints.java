package calculator.domain;

public enum NumberConstraints {
    MAX_VALUE(1_000_000_000),
    MAX_NUMBER_COUNT(30),
    MAX_NUMBER_LENGTH(9);

    private final int value;

    NumberConstraints(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
