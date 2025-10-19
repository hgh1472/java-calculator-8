package calculator.interfaces;

import java.util.List;

public class IOHandler {
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public IOHandler(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public List<Long> requestNumbers() {
        outputHandler.printRequestMessage();
        return inputHandler.read();
    }
}
