package calculator.interfaces;

public class OutputHandler {
    public void printRequestMessage() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
    }

    public void printResult(Long result) {
        System.out.println("결과 : " + result);
    }
}
