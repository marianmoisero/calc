package mm.calculator.exceptions;

public class IllegalEvaluatorStateException extends Exception {

	private static final long serialVersionUID = 3380409661148045547L;

	public IllegalEvaluatorStateException(String message) {
		super(message);
	}

	public IllegalEvaluatorStateException(String message, Throwable cause) {
		super(message, cause);
	}

}
