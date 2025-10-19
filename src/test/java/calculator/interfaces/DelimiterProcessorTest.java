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

    @Nested
    @DisplayName("커스텀 구분자 추출 시,")
    class ExtractCustomDelimiters {
        @Test
        @DisplayName("입력값이 null일 때 IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenInputIsNull() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.extractCustomDelimiters(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 입력 값이 비어 있습니다.");
        }

        @Test
        @DisplayName("커스텀 구분자가 없을 경우, 기본 구분자 집합을 반환한다.")
        void returnDefaultDelimiters_whenNoCustomDelimiter() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            var delimiters = delimiterProcessor.extractCustomDelimiters("1,2:3");

            assertThat(delimiters).containsExactlyInAnyOrder(",", ":");
        }

        @Test
        @DisplayName("커스텀 구분자가 있을 경우, 기본 구분자와 커스텀 구분자를 포함한 집합을 반환한다.")
        void returnDelimitersIncludingCustomDelimiter_whenCustomDelimiterExists() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            var delimiters = delimiterProcessor.extractCustomDelimiters("//;\\n1;2;3");

            assertThat(delimiters).containsExactlyInAnyOrder(",", ":", ";");
        }

        @Test
        @DisplayName("커스텀 구분자가 단일 문자가 아닐 경우, IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenCustomDelimiterIsNotSingleCharacter() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.extractCustomDelimiters("//;;\\n1;;2;;3"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 커스텀 구분자는 단일 문자여야 합니다.");
        }

        @Test
        @DisplayName("커스텀 구분자가 공백 문자일 경우, IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenCustomDelimiterIsWhitespace() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.extractCustomDelimiters("// \\n1 2 3"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 커스텀 구분자는 제어 문자 또는 공백 문자가 될 수 없습니다.");
        }

        @Test
        @DisplayName("커스텀 구분자가 제어 문자일 경우, IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenCustomDelimiterIsControlCharacter() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.extractCustomDelimiters("//\n\\n1\n2\n3"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 커스텀 구분자는 제어 문자 또는 공백 문자가 될 수 없습니다.");
        }

        @Test
        @DisplayName("커스텀 구분자가 숫자일 경우, IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenCustomDelimiterIsDigit() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.extractCustomDelimiters("//1\\n11213"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 커스텀 구분자는 숫자가 될 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("커스텀 구분자 접두사 제거 시,")
    class RemoveCustomPrefix {
        @Test
        @DisplayName("입력값이 null일 때 IllegalArgumentException 예외가 발생한다.")
        void throwIllegalArgumentException_whenInputIsNull() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            assertThatThrownBy(() -> delimiterProcessor.removeCustomPrefix(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 입력 값이 비어 있습니다.");
        }

        @Test
        @DisplayName("커스텀 구분자가 없을 경우, 입력값을 그대로 반환한다.")
        void returnInputAsIs_whenNoCustomDelimiter() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            String result = delimiterProcessor.removeCustomPrefix("1,2:3");

            assertThat(result).isEqualTo("1,2:3");
        }

        @Test
        @DisplayName("커스텀 구분자가 있을 경우, 커스텀 구분자 접두사를 제거한 값을 반환한다.")
        void returnInputWithoutCustomPrefix_whenCustomDelimiterExists() {
            DelimiterProcessor delimiterProcessor = new DelimiterProcessor();

            String result = delimiterProcessor.removeCustomPrefix("//;\\n1;2;3");

            assertThat(result).isEqualTo("1;2;3");
        }
    }
}