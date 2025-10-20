package calculator;

import calculator.domain.Calculator;
import calculator.interfaces.Converter;
import calculator.interfaces.DelimiterProcessor;
import calculator.interfaces.IOHandler;
import calculator.interfaces.InputHandler;
import calculator.interfaces.OutputHandler;
import calculator.interfaces.Separator;

public class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        InputHandler inputHandler = new InputHandler(new Separator(), new DelimiterProcessor(), new Converter());
        OutputHandler outputHandler = new OutputHandler();
        IOHandler ioHandler = new IOHandler(inputHandler, outputHandler);

        App app = new App(calculator, ioHandler);

        app.run();
    }
}
