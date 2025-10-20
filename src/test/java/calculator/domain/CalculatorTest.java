package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CalculatorTest {

    @Nested
    @DisplayName("숫자들을 더할 때,")
    class Add {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("빈 값의 숫자들이 들어오면, RuntimeException을 발생시킨다.")
        void throwRuntimeException_whenNumbersIsNull(List<Long> numbers) {
            Calculator calculator = new Calculator();

            assertThatThrownBy(() -> calculator.add(numbers))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("[ERROR] 계산할 숫자가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("null 값이 포함된 숫자들이 들어오면, RuntimeException을 발생시킨다.")
        void throwRuntimeException_whenNumbersContainsNull() {
            Calculator calculator = new Calculator();
            Long nullNum = null;
            List<Long> numbers = new ArrayList<>();
            numbers.add(1L);
            numbers.add(nullNum);

            assertThatThrownBy(() -> calculator.add(numbers))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("[ERROR] null 값은 계산할 수 없습니다.");
        }

        @Test
        @DisplayName("숫자가 30개 초과로 들어오면, IllegalArgumentException을 발생시킨다.")
        void throwIllegalArgumentException_whenNumbersExceedsLimit() {
            Calculator calculator = new Calculator();
            List<Long> numbers = new ArrayList<>();
            for (long i = 1; i <= 31; i++) {
                numbers.add(i);
            }

            assertThatThrownBy(() -> calculator.add(numbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 숫자는 최대 30개까지만 사용할 수 있습니다.");
        }

        @Test
        @DisplayName("음수 값이 포함된 숫자들이 들어오면, IllegalArgumentException을 발생시킨다.")
        void throwIllegalArgumentException_whenNumbersContainsNegative() {
            Calculator calculator = new Calculator();
            List<Long> numbers = new ArrayList<>();
            numbers.add(1L);
            numbers.add(-2L);

            assertThatThrownBy(() -> calculator.add(numbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 숫자는 양수만 사용할 수 있습니다.");
        }

        @Test
        @DisplayName("각 숫자가 9자리(100,000,000)를 초과하는 값이 포함된 숫자들이 들어오면, IllegalArgumentException을 발생시킨다.")
        void throwIllegalArgumentException_whenNumbersContainsTooLargeValue() {
            Calculator calculator = new Calculator();
            List<Long> numbers = new ArrayList<>();
            numbers.add(1L);
            numbers.add(1_000_000_000L);

            assertThatThrownBy(() -> calculator.add(numbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 각 숫자는 최대 9자리까지 가능합니다.");
        }

        @Test
        @DisplayName("각 숫자들을 더한 값을 반환한다.")
        void add() {
            Calculator calculator = new Calculator();
            List<Long> numbers = new ArrayList<>();
            numbers.add(1L);
            numbers.add(2L);
            numbers.add(3L);

            Long result = calculator.add(numbers);

            assertThat(result).isEqualTo(6L);
        }
    }
}