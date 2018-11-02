package mm.calculator.exceptions;

public class InvalidExpressionException extends Exception {

	private static final long serialVersionUID = 1905550907740214198L;

	public InvalidExpressionException(String message) {
		super(message);
	}

	public InvalidExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

}
