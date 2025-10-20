package calculator.interfaces;

import java.util.HashSet;
import java.util.Set;

public class DelimiterProcessor {
    private static final String CUSTOM_DELIMITER_PREFIX = "//";
    private static final String CUSTOM_DELIMITER_SUFFIX = "\\n";
    private static final int CUSTOM_DELIMITER_LENGTH = 1;
    private static final Set<String> DEFAULT_DELIMITERS = Set.of(",", ":");

    public boolean hasCustomDelimiter(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (input.length() <= getCustomDelimiterRange()) {
            return false;
        }

        return input.startsWith(CUSTOM_DELIMITER_PREFIX) && input.contains(CUSTOM_DELIMITER_SUFFIX);
    }

    public Set<String> extractDelimiters(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (!hasCustomDelimiter(input)) {
            return DEFAULT_DELIMITERS;
        }

        int startIndex = CUSTOM_DELIMITER_PREFIX.length();
        int endIndex = input.indexOf(CUSTOM_DELIMITER_SUFFIX);
        String customDelimiter = input.substring(startIndex, endIndex);

        validateCustomDelimiter(customDelimiter);

        Set<String> delimiters = new HashSet<>(DEFAULT_DELIMITERS);
        delimiters.add(customDelimiter);

        return delimiters;
    }

    public String removeCustomPrefix(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (!hasCustomDelimiter(input)) {
            return input;
        }

        int startIndex = input.indexOf(CUSTOM_DELIMITER_SUFFIX) + CUSTOM_DELIMITER_SUFFIX.length();
        if (startIndex != getCustomDelimiterRange()) {
            throw new IllegalArgumentException("[ERROR] 커스텀 구분자는 단일 문자여야 합니다.");
        }
        return input.substring(startIndex);
    }

    private static int getCustomDelimiterRange() {
        return CUSTOM_DELIMITER_PREFIX.length() + CUSTOM_DELIMITER_LENGTH + CUSTOM_DELIMITER_SUFFIX.length();
    }

    private void validateCustomDelimiter(String customDelimiter) {
        if (customDelimiter.length() != CUSTOM_DELIMITER_LENGTH) {
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
