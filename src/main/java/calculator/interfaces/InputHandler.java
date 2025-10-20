package calculator.interfaces;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.Set;

public class InputHandler {
    private final Separator separator;
    private final DelimiterProcessor delimiterProcessor;
    private final Converter converter;

    public InputHandler(Separator separator, DelimiterProcessor delimiterProcessor, Converter converter) {
        this.separator = separator;
        this.delimiterProcessor = delimiterProcessor;
        this.converter = converter;
    }

    public List<Long> read() {
        try {
            String input = Console.readLine();
            if (input == null || input.isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 빈 문자열은 입력할 수 없습니다.");
            }
            Set<String> delimiters = delimiterProcessor.extractDelimiters(input);
            if (delimiterProcessor.hasCustomDelimiter(input)) {
                input = delimiterProcessor.removeCustomDelimiterString(input);
            }
            List<String> separated = separator.separate(input, delimiters);
            return converter.convert(separated);
        } finally {
            Console.close();
        }
    }
}
