package mm.calculator.exceptions;

public class InvalidOperatorException extends Exception {

	private static final long serialVersionUID = 403727097238610124L;

	public InvalidOperatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOperatorException(String message) {
		super(message);
	}

}
