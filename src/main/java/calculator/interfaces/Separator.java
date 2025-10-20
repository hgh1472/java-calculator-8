package calculator.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Separator {

    public List<String> separate(String input, Set<String> delimiters) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 비어 있습니다.");
        }
        if (delimiters == null || delimiters.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 구분자가 존재하지 않습니다.");
        }

        String delimiterRegex = delimiters.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));

        return Arrays.stream(input.split(delimiterRegex))
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
