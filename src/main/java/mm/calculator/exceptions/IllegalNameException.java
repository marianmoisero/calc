package mm.calculator.exceptions;

public class IllegalNameException extends Exception {

	private static final long serialVersionUID = 6239100762864165042L;

	public IllegalNameException(String message) {
		super(message);
	}

	public IllegalNameException(String message, Throwable cause) {
		super(message, cause);
	}

}
