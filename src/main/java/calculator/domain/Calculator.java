package calculator.domain;

import java.util.List;

public class Calculator {
    private static final int MAX_NUMBER_COUNT_LIMIT = 30;
    private static final int MAX_NUMBER_LIMIT = 100000000;

    public Long add(List<Long> numbers) {
        validateNumbers(numbers);

        Long result = 0L;
        for (Long number : numbers) {
            validateNumber(number);
            result += number;
        }

        return result;
    }

    private static void validateNumbers(List<Long> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new RuntimeException("[ERROR] 계산할 숫자가 존재하지 않습니다.");
        }
        if (numbers.size() > MAX_NUMBER_COUNT_LIMIT) {
            throw new IllegalArgumentException("[ERROR] 숫자는 최대 30개까지만 사용할 수 있습니다.");
        }
    }

    private static void validateNumber(Long number) {
        if (number == null) {
            throw new RuntimeException("[ERROR] null 값은 계산할 수 없습니다.");
        }
        if (number < 0) {
            throw new IllegalArgumentException("[ERROR] 숫자는 양수만 사용할 수 있습니다.");
        }
        if (number >= MAX_NUMBER_LIMIT) {
            throw new IllegalArgumentException("[ERROR] 각 숫자는 최대 9자리까지 가능합니다.");
        }
    }
}
