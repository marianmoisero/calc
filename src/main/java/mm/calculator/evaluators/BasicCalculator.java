package mm.calculator.evaluators;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import mm.calculator.exceptions.IllegalEvaluatorStateException;
import mm.calculator.exceptions.IllegalNameException;
import mm.calculator.exceptions.InvalidExpressionException;

public class BasicCalculator {

	// TODO: PATTERN compile......

	static final String REGEX_ALL_TOKENS = "(?=[\\,\\s+()])|(?<=[\\,\\s+()])";
	static final String REGEX_NUMERIC = "[+-]?\\d*\\.?\\d+";
	static final String REGEX_VARIABLE = "[a-z]";
	static final String REGEX_WHITESPACE = "\\s+";
	static final String REGEX_COMMA = "\\,";
	static final String REGEX_LEFT_BRACKET = "\\(";
	static final String REGEX_RIGHT_BRACKET = "\\)";

	public static void main(String[] args) throws Exception {
		BasicCalculator calc = new BasicCalculator();

//		System.out.println(calc.evaluate("add(1,2)")); // 3
//		System.out.println(calc.evaluate("sub(1,2)")); // -1
//		System.out.println(calc.evaluate("mult(1,2)")); // 2
//		System.out.println(calc.evaluate("div(1,2)")); // 0.5
//
//		System.out.println(calc.evaluate("add(1,mult(2,3))")); // 7
//		System.out.println(calc.evaluate("sub(1,add(1,2))")); // -2
//		System.out.println(calc.evaluate("mult(1,div(1,2))")); // 0.5
//		System.out.println(calc.evaluate("div(1,mult(2,1))")); // 0.5
//
//		System.out.println(calc.evaluate("add(add(2,3),mult(2,3))")); // 11
//		System.out.println(calc.evaluate("sub(mult(1,2),add(1,2))")); // -1
//		System.out.println(calc.evaluate("mult(add(2,2),div(9,3))")); // 12
//		System.out.println(calc.evaluate("div(div(1,2),mult(2,1))")); // 0.25
//
//		System.out.println(calc.evaluate("let(a,5,add(a,a))")); // 10
//		System.out.println(calc.evaluate("let(a,5,let(b,mult(a,10),add(b,a)))")); // 55
		System.out.println(calc.evaluate("let(a, let(b, 10, add(b, b)), let(b, 20,	 add(a, b)))")); // 40
//
//		System.out.println(calc.evaluate("let(a,5,add(1,a))")); // 6
//		System.out.println(calc.evaluate("let(a,5,let(b,mult(a,10),let(c,add(a,b),add(c,a))))")); // 60
	}

	
	public double evaluate(String expression)
			throws IllegalNameException, IllegalEvaluatorStateException, InvalidExpressionException {
		Stack<String> values = new Stack<String>();
		Stack<String> operators = new Stack<String>();
		Stack<String> variablesToBeDiscarded = new Stack<String>();
		Map<String, String> variables = new LinkedHashMap<String, String>();

		String[] tokens = getTokens(expression);
		for (String token : tokens) {
			if (isNumeric(token)) {
				values.push(token);
			} else if (isVariable(token)) {
				values.push(token);
			} else if (isLeftBracket(token)) {
				continue;
			} else if (isRightBracket(token)) {
				if (operators.empty()) {
					throw new IllegalEvaluatorStateException("Operators stack shouldn't have been empty");
				}
				
				String op = operators.pop();
				if (isLetOperator(op)) {
					String var = variablesToBeDiscarded.pop(); // TODO: exception handling
					variables.remove(var); // TODO: exception handling
				} else if (isBinaryOperator(op)) {
					String result = evaluateBinaryOp(op, values.pop(), values.pop(), variables);
					values.push(result);
				} else {
					throw new IllegalNameException("Unknown operator:" + op);
				}
			} else if (isOperator(token)) {
				if (!values.empty() && isNumeric(values.peek()) && !operators.isEmpty()
						&& isLetOperator(operators.peek())) {
					String val = values.pop();
					String key = values.pop();
					variables.put(key, val);
					variablesToBeDiscarded.push(key);
				}
				operators.push(token);
			} else if (isComma(token)) {
				continue;
			} else if (isWhitespace(token)) {
				continue;
			} else {
				System.out.println("Unknown token:" + token);
				throw new IllegalNameException("Invalid token:" + token);
			}
		}

		if(!variables.isEmpty() || !variablesToBeDiscarded.empty()) {
			throw new InvalidExpressionException("Please check the expression as some closing brackets might be missing");
		}

		return Double.valueOf(values.pop());
	}

	String[] getTokens(String expression) {
		return expression.split(REGEX_ALL_TOKENS);
	}

	boolean isOperator(String token) {
		if ("LET".equalsIgnoreCase(token) || "ADD".equalsIgnoreCase(token) || "SUB".equalsIgnoreCase(token)
				|| "MULT".equalsIgnoreCase(token) || "DIV".equalsIgnoreCase(token)) {
			return true;
		}
		return false;
	}

	boolean isBinaryOperator(String token) {
		if ("ADD".equalsIgnoreCase(token) || "SUB".equalsIgnoreCase(token) || "MULT".equalsIgnoreCase(token)
				|| "DIV".equalsIgnoreCase(token)) {
			return true;
		}
		return false;
	}

	boolean isLetOperator(String token) {
		if ("LET".equalsIgnoreCase(token)) {
			return true;
		}
		return false;
	}

	Double evaluateBinaryOp(String op, Double val2, Double val1) {
		String upperCaseOp = op.toUpperCase();
		switch (upperCaseOp) {
		case "ADD":
			return val1 + val2;
		case "SUB":
			return val1 - val2;
		case "MULT":
			return val1 * val2;
		case "DIV":
			return val1 / val2; // TODO: val2==0 throw ex
		default:
			System.out.println("Unknown binary op:" + op);
			return 0.0; // TODO: throw ex
		}
	}

	String evaluateBinaryOp(String op, String var1, String var2, Map<String, String> vars) {
		String val1 = isVariable(var1) ? vars.get(var1) : var1; // TODO: Double.valueOf ex.
		String val2 = isVariable(var2) ? vars.get(var2) : var2;
		Double num1 = Double.valueOf(val1);
		Double num2 = Double.valueOf(val2);
		Double ret = evaluateBinaryOp(op, num1, num2);
		return ret.toString();
	}

	boolean isNumeric(String token) {
		return token.matches(REGEX_NUMERIC);
	}

	boolean isVariable(String token) {
		return token.matches(REGEX_VARIABLE);
	}

	boolean isWhitespace(String token) {
		return token.matches(REGEX_WHITESPACE);
	}

	boolean isComma(String token) {
		return token.matches(REGEX_COMMA);
	}

	boolean isRightBracket(String token) {
		return token.matches(REGEX_RIGHT_BRACKET);
	}

	boolean isLeftBracket(String token) {
		return token.matches(REGEX_LEFT_BRACKET);
	}
}
