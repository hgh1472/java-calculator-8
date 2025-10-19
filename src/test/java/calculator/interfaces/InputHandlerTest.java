package calculator.interfaces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputHandlerTest {
    private InputHandler inputHandler;

    @BeforeEach
    void setUp() {
        inputHandler = new InputHandler(new Separator(), new DelimiterProcessor());
    }

    @Test
    @DisplayName("입력값이 빈 문자열일 경우, IllegalArgumentException을 발생시킨다.")
    void throwIllegalArgumentException_whenInputIsEmpty() {
        ByteArrayInputStream in = new ByteArrayInputStream("\n".getBytes());
        System.setIn(in);

        assertThatThrownBy(() -> inputHandler.read())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 문자열은 입력할 수 없습니다.");
    }
}