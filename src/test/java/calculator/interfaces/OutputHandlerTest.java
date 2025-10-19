package calculator.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import calculator.ConsoleSupport;
import java.io.OutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OutputHandlerTest extends ConsoleSupport {

    @Test
    @DisplayName("덧셈 문자열 입력 요청 메세지를 출력한다.")
    void printRequestMessage() {
        OutputStream out = setOutput();
        OutputHandler outputHandler = new OutputHandler();

        outputHandler.printRequestMessage();

        assertThat("덧셈할 문자열을 입력해 주세요.\n").isEqualTo(out.toString());
    }

    @Test
    @DisplayName("덧셈 결과를 출력한다.")
    void printResult() {
        OutputStream out = setOutput();
        OutputHandler outputHandler = new OutputHandler();

        outputHandler.printResult(30L);

        assertThat("결과 : 30\n").isEqualTo(out.toString());
    }
}