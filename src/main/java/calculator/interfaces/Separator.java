package calculator.interfaces;

import static calculator.domain.NumberConstraints.MAX_NUMBER_LENGTH;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Separator {

    public List<Long> separate(String input, Set<String> delimiters) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (delimiters == null || delimiters.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 구분자가 존재하지 않습니다.");
        }

        String delimiterRegex = delimiters.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));

        List<Long> numbers = new ArrayList<>();
        String[] token = input.split(delimiterRegex);
        for (String numberString : token) {
            if (numberString.isEmpty()) {
                continue;
            }
            validateNumberString(numberString);
            numbers.add(Long.parseLong(numberString));
        }
        return numbers;
    }

    private void validateNumberString(String str) {
        if (isNotDecimal(str)) {
            throw new IllegalArgumentException("[ERROR] 허용되지 않는 문자가 포함되어 있습니다.");
        }
        if (str.length() >= MAX_NUMBER_LENGTH.getValue()) {
            throw new IllegalArgumentException("[ERROR] 각 숫자는 최대 9자리까지 가능합니다.");
        }
    }

    private static boolean isNotDecimal(String str) {
        return !str.matches("-?\\d+");
    }
}
