package calculator.interfaces;

public class DelimiterProcessor {

    public static final int CUSTOM_DELIMITER_POSSIBLE_LENGTH = 4;

    public boolean hasCustomDelimiter(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (input.length() <= CUSTOM_DELIMITER_POSSIBLE_LENGTH) {
            return false;
        }
        return input.startsWith("//") && input.substring(3, 5).equals("\\n");
    }
}
