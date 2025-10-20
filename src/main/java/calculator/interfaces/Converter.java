package calculator.interfaces;

import static calculator.domain.NumberConstraints.MAX_NUMBER_LENGTH;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public List<Long> convert(List<String> strings) {
        List<Long> numbers = new ArrayList<>();
        for (String numberString : strings) {
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
