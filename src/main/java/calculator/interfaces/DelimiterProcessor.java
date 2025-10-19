package calculator.interfaces;

import java.util.HashSet;
import java.util.Set;

public class DelimiterProcessor {

    private static final int CUSTOM_DELIMITER_POSSIBLE_LENGTH = 4;
    private static final Set<String> DEFAULT_DELIMITERS = Set.of(",", ":");

    public boolean hasCustomDelimiter(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (input.length() <= CUSTOM_DELIMITER_POSSIBLE_LENGTH) {
            return false;
        }

        return input.startsWith("//") && input.contains("\\n");
    }

    public Set<String> extractCustomDelimiters(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (!hasCustomDelimiter(input)) {
            return DEFAULT_DELIMITERS;
        }

        int startIndex = 2;
        int endIndex = input.indexOf("\\n");
        String customDelimiter = input.substring(startIndex, endIndex);

        validateCustomDelimiter(customDelimiter);

        Set<String> delimiters = new HashSet<>(DEFAULT_DELIMITERS);
        delimiters.add(customDelimiter);

        return delimiters;
    }

    private static void validateCustomDelimiter(String customDelimiter) {
        if (customDelimiter.length() != 1) {
            throw new IllegalArgumentException("[ERROR] 커스텀 구분자는 단일 문자여야 합니다.");
        }
        char delimiterChar = customDelimiter.charAt(0);
        if (Character.isISOControl(delimiterChar) || Character.isWhitespace(delimiterChar)) {
            throw new IllegalArgumentException("[ERROR] 커스텀 구분자는 제어 문자 또는 공백 문자가 될 수 없습니다.");
        }
        if (Character.isDigit(delimiterChar)) {
            throw new IllegalArgumentException("[ERROR] 커스텀 구분자는 숫자가 될 수 없습니다.");
        }
    }
}
