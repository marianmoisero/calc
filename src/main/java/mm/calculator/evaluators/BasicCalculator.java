package mm.calculator.evaluators;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import mm.calculator.exceptions.IllegalNameException;
import mm.calculator.exceptions.InvalidEvaluatorStateException;
import mm.calculator.exceptions.InvalidExpressionException;
import mm.calculator.exceptions.InvalidOperatorException;
import mm.calculator.utils.OperatorUtils;
import mm.calculator.utils.ParserUtils;

public class BasicCalculator {

	public BasicCalculator() {
	}

	public double evaluate(String expression) throws InvalidEvaluatorStateException, InvalidOperatorException,
			IllegalNameException, InvalidExpressionException {
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
					throw new InvalidEvaluatorStateException("Operators stack shouldn't have been empty");
				}
				String op = operators.pop();
				
				if (isLetOperator(op)) {
					if (variablesToBeDiscarded.empty() || variables.isEmpty()) {
						throw new InvalidEvaluatorStateException("Variables stack or map shouldn't have been empty");
					}
					String var = variablesToBeDiscarded.pop();
					variables.remove(var);
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
				throw new IllegalNameException("Invalid token:" + token);
			}
		}

		if (!variables.isEmpty() || !variablesToBeDiscarded.empty()) {
			throw new InvalidExpressionException(
					"Please check the expression as some closing brackets might be missing");
		}

		return Double.valueOf(values.pop());
	}

	boolean isOperator(String token) throws InvalidOperatorException {
		return OperatorUtils.isOperator(token);
	}

	boolean isBinaryOperator(String token) throws InvalidOperatorException {
		return OperatorUtils.isBinaryOperator(token);
	}

	boolean isLetOperator(String token) throws InvalidOperatorException {
		return OperatorUtils.isLetOperator(token);
	}

	Double evaluateBinaryOp(String op, Double val2, Double val1)
			throws InvalidOperatorException, InvalidExpressionException {
		return OperatorUtils.evaluateBinaryOp(op, val2, val1);
	}

	String evaluateBinaryOp(String op, String var1, String var2, Map<String, String> vars)
			throws InvalidEvaluatorStateException {
		Double ret = 0.0;
		try {
			String val1 = isVariable(var1) ? vars.get(var1) : var1;
			String val2 = isVariable(var2) ? vars.get(var2) : var2;
			Double num1 = Double.valueOf(val1);
			Double num2 = Double.valueOf(val2);
			ret = evaluateBinaryOp(op, num1, num2);
		} catch (Exception e) {
			throw new InvalidEvaluatorStateException("Issues evaluating expression:" + var1 + op + var2, e);
		}
		return ret.toString();
	}

	public String[] getTokens(String expression) {
		return ParserUtils.getTokens(expression);
	}

	public boolean isNumeric(String token) {
		return ParserUtils.isNumeric(token);
	}

	public boolean isVariable(String token) {
		return ParserUtils.isVariable(token);
	}

	public boolean isWhitespace(String token) {
		return ParserUtils.isWhitespace(token);
	}

	public boolean isComma(String token) {
		return ParserUtils.isComma(token);
	}

	public boolean isRightBracket(String token) {
		return ParserUtils.isRightBracket(token);
	}

	public boolean isLeftBracket(String token) {
		return ParserUtils.isLeftBracket(token);
	}

}
