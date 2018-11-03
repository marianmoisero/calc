package mm.calculator.exceptions;

public class InvalidEvaluatorStateException extends Exception {

	private static final long serialVersionUID = 3380409661148045547L;

	public InvalidEvaluatorStateException(String message) {
		super(message);
	}

	public InvalidEvaluatorStateException(String message, Throwable cause) {
		super(message, cause);
	}

}
