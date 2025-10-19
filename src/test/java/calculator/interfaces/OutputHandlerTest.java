package calculator.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OutputHandlerTest {

    @Test
    @DisplayName("덧셈 문자열 입력 요청 메세지를 출력한다.")
    void printRequestMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        OutputHandler outputHandler = new OutputHandler();

        outputHandler.printRequestMessage();

        assertThat("덧셈할 문자열을 입력해 주세요.\n").isEqualTo(out.toString());
    }
}