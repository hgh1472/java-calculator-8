package calculator.interfaces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConverterTest {

    @Test
    @DisplayName("구분자와 숫자 외 문자가 포함된 경우, IllegalArgumentException 예외가 발생한다.")
    void throwIllegalArgumentException_whenNotAllowed() {
        Converter converter = new Converter();

        assertThatThrownBy(() -> converter.convert(List.of("1", "2", "a")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 허용되지 않는 문자가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("숫자가 9자리를 초과하는 경우, IllegalArgumentException 예외가 발생한다.")
    void throwIllegalArgumentException_whenExceedNineDigits() {
        Converter converter = new Converter();

        assertThatThrownBy(() -> converter.convert(List.of("1234567890")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 각 숫자는 최대 9자리까지 가능합니다.");
    }


}