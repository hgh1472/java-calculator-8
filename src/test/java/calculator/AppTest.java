package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import calculator.domain.Calculator;
import calculator.interfaces.Converter;
import calculator.interfaces.DelimiterProcessor;
import calculator.interfaces.IOHandler;
import calculator.interfaces.InputHandler;
import calculator.interfaces.OutputHandler;
import calculator.interfaces.Separator;
import java.io.OutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AppTest extends ConsoleSupport {
    private final App app =
            new App(new Calculator(),
                    new IOHandler(
                            new InputHandler(new Separator(), new DelimiterProcessor(), new Converter()),
                            new OutputHandler()
                    )
            );

    @Test
    @DisplayName("빈 문자열을 입력할 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenInputIsEmptyString() {
        String input = "\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 문자열은 입력할 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,a", "4:5:six", "//;\n4;9", "1*2*3", "1+2+3", "1,2, 3"
    })
    @DisplayName("구분자와 숫자 외 문자가 포함된 문자열을 입력할 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenInputContainsInvalidCharacters(String input) {
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 허용되지 않는 문자가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("숫자가 9자리 초과일 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenNumberExceedsNineDigits() {
        String input = "1234567890,2,3\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 각 숫자는 최대 9자리까지 가능합니다.");
    }

    @Test
    @DisplayName("커스텀 구분자가 공백문자일 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenCustomDelimiterIsWhitespace() {
        String input = "// \\n1 2 3\n";
        setInput(input);
        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 커스텀 구분자는 제어 문자 또는 공백 문자가 될 수 없습니다.");
    }

    @Test
    @DisplayName("커스텀 구분자가 제어문자일 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenCustomDelimiterIsControlCharacter() {
        String input = "//\t\\n1\t2\t3\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 커스텀 구분자는 제어 문자 또는 공백 문자가 될 수 없습니다.");
    }

    @Test
    @DisplayName("커스텀 구분자가 숫자일 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenCustomDelimiterIsDigit() {
        String input = "//1\\n123123123,2,3\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 커스텀 구분자는 숫자가 될 수 없습니다.");
    }

    @Test
    @DisplayName("커스텀 구분자가 2자 이상일 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenCustomDelimiterIsMoreThanOneCharacter() {
        String input = "//;;\\n1;;2;;3\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 커스텀 구분자는 단일 문자여야 합니다.");
    }

    @Test
    @DisplayName("숫자가 존재하지 않는 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenNoNumbersExist() {
        String input = "//;\\n;;;\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 계산할 숫자가 존재하지 않습니다.");
    }


    @Test
    @DisplayName("숫자가 음수일 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenNumberIsNegative() {
        String input = "1,2,-3,4\n";
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 숫자는 양수만 사용할 수 있습니다.");
    }

    @Test
    @DisplayName("숫자의 개수가 30개를 초과하는 경우, IllegalArgumentException 예외를 발생시킨다.")
    void throwIllegalArgumentException_whenNumberCountExceedsLimit() {
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 0; i < 31; i++) {
            inputBuilder.append("1");
            if (i < 30) {
                inputBuilder.append(",");
            }
        }
        inputBuilder.append("\n");
        String input = inputBuilder.toString();
        setInput(input);

        assertThatThrownBy(app::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 숫자는 최대 30개까지만 사용할 수 있습니다.");
    }

    @Test
    @DisplayName("입력된 숫자들을 더한 결과를 출력한다.")
    void calculate() {
        String input = "//s\\n1,2:3s4s5\n";
        setInput(input);
        OutputStream out = setOutput();

        app.run();

        assertThat(out.toString()).contains("15");
    }
}
