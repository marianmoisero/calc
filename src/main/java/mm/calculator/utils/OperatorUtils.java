package mm.calculator.utils;

import mm.calculator.entities.Operator;
import mm.calculator.exceptions.InvalidExpressionException;
import mm.calculator.exceptions.InvalidOperatorException;

public final class OperatorUtils {

	public static Operator getOp(String op) throws InvalidOperatorException {
		try {
			return Operator.valueOf(op.toUpperCase());
		} catch (Exception e) {
			throw new InvalidOperatorException("Unknown operator:" + op);
		}
	}

	public static boolean isOperator(String token) {
		return isLetOperator(token) || isBinaryOperator(token);
	}

	public static boolean isBinaryOperator(String token) {
		try {
			Operator op = getOp(token);
			return Operator.ADD == op || Operator.SUB == op || Operator.MULT == op || Operator.DIV == op;
		} catch (InvalidOperatorException e) {
			return false;
		}

	}

	public static boolean isLetOperator(String token) {
		try {
			Operator op = getOp(token);
			return Operator.LET == op;
		} catch (InvalidOperatorException e) {
			return false;
		}
	}

	public static Double evaluateBinaryOp(String op, Double val2, Double val1)
			throws InvalidOperatorException, InvalidExpressionException {
		Operator operator = getOp(op);

		switch (operator) {
		case ADD:
			return val1 + val2;
		case SUB:
			return val1 - val2;
		case MULT:
			return val1 * val2;
		case DIV:
			if (0.0 == val2) {
				throw new InvalidExpressionException("Can't divide with 0:" + val1 + op + val2);
			}
			return val1 / val2;
		default:
			throw new InvalidOperatorException("No operation defined for operator:" + operator);
		}
	}

}
