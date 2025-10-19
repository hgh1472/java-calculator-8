package calculator.interfaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DelimiterProcessorTest {

    @Nested
    @DisplayName("커스텀 구분자 여부 확인 시,")
    class HasCustomDelimiter {
        @Test
        @DisplayName("입력값이 null일 때 IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenInputIsNull() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.hasCustomDelimiter(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 입력 값이 비어 있습니다.");
        }

        @Test
        @DisplayName("입력값이 4글자 이하일 때, 커스텀 구분자를 가질 수 없다.")
        void returnFalse_whenInputLengthIsLessThanOrEqualTo4() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            boolean hasCustomDelimiter = delimiterProcessor.hasCustomDelimiter("//\\n");

            assertThat(hasCustomDelimiter).isFalse();
        }

        @Test
        @DisplayName("문자열 앞부분의 \"//\"와 \"\\n\" 사이에 위치하는 문자가 있다면, 커스텀 구분자가 있다고 판단한다.")
        void returnTrue_whenInputHasCustomDelimiter() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            boolean hasCustomDelimiter = delimiterProcessor.hasCustomDelimiter("//;\\n1;2;3");

            assertThat(hasCustomDelimiter).isTrue();
        }

        @Test
        @DisplayName("커스텀 구분자는 단일 문자이기에, \"//\"와 \"\\n\" 사이에 2글자 이상이 위치할 경우, 커스텀 구분자를 가진다고 판단하지 않는다.")
        void returnFalse_whenCustomDelimiterIsMoreThanOneCharacter() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            boolean hasCustomDelimiter = delimiterProcessor.hasCustomDelimiter("//;;\\n1;;2;;3");

            assertThat(hasCustomDelimiter).isFalse();
        }
    }
}