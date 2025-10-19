package calculator;

import calculator.domain.Calculator;
import calculator.interfaces.IOHandler;
import java.util.List;

public class App {
    private final Calculator calculator;
    private final IOHandler ioHandler;

    public App(Calculator calculator, IOHandler ioHandler) {
        this.calculator = calculator;
        this.ioHandler = ioHandler;
    }

    public void run() {
        List<Long> numbers = ioHandler.requestNumbers();
        Long result = calculator.add(numbers);
        ioHandler.printResult(result);
    }
}
