package calculator.interfaces;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.Set;

public class InputHandler {
    private final Separator separator;
    private final DelimiterProcessor delimiterProcessor;

    public InputHandler(Separator separator, DelimiterProcessor delimiterProcessor) {
        this.separator = separator;
        this.delimiterProcessor = delimiterProcessor;
    }

    public List<Long> read() {
        String input = Console.readLine();
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 문자열은 입력할 수 없습니다.");
        }
        Set<String> delimiters = delimiterProcessor.extractDelimiters(input);
        if (delimiterProcessor.hasCustomDelimiter(input)) {
            input = delimiterProcessor.removeCustomPrefix(input);
        }
        return separator.separate(input, delimiters);
    }
}
