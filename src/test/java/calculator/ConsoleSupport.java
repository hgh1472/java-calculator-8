package calculator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;

public abstract class ConsoleSupport {
    private final InputStream inputStream = System.in;
    private final OutputStream outputStream = new ByteArrayOutputStream();

    @AfterEach
    void tearDown() throws IOException {
        outputStream.close();
        inputStream.close();
    }

    protected void setInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    protected OutputStream setOutput() {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }
}
