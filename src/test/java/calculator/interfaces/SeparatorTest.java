package calculator.interfaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class SeparatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("입력 문자열이 null이거나 빈 문자열인 경우, IllegalArgumentException 예외가 발생한다.")
    void throwIllegalArgumentException_whenNullOrEmpty(String input) {
        Separator separator = new Separator();

        assertThatThrownBy(() -> separator.separate(input, Set.of(",")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 입력 값이 비어 있습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("구분자 집합이 null이거나 빈 집합인 경우, IllegalArgumentException 예외가 발생한다.")
    void throwIllegalArgumentException_whenDelimitersNullOrEmpty(Set<String> delimiters) {
        Separator separator = new Separator();

        assertThatThrownBy(() -> separator.separate("1,2,3", delimiters))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구분자가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("구분자와 숫자 외 문자가 포함된 경우, IllegalArgumentException 예외가 발생한다.")
    void throwIllegalArgumentException_whenNotAllowed() {
        Separator separator = new Separator();

        assertThatThrownBy(() -> separator.separate("1,2,4A", Set.of(",")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 허용되지 않는 문자가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("숫자가 9자리를 초과하는 경우, IllegalArgumentException 예외가 발생한다.")
    void throwIllegalArgumentException_whenExceedNineDigits() {
        Separator separator = new Separator();

        assertThatThrownBy(() -> separator.separate("1,2,123456789", Set.of(",")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 각 숫자는 최대 9자리까지 가능합니다.");
    }

    @Test
    @DisplayName("여러 구분자를 사용하는 경우, 각 구분자를 인식하여 숫자를 분리한다.")
    void separateWithMultipleDelimiters() {
        Separator separator = new Separator();

        List<Long> result = separator.separate("1;2,3|4", Set.of(",", ";", "|"));

        assertThat(result).containsExactly(1L, 2L, 3L, 4L);
    }

    @Test
    @DisplayName("구분자만 있고 숫자는 존재하지 않는 경우, 빈 리스트를 반환한다.")
    void returnEmptyList_whenOnlyDelimiters() {
        Separator separator = new Separator();

        List<Long> result = separator.separate(",,,;;;", Set.of(",", ";"));

        assertThat(result).isEmpty();
    }
}