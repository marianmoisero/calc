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
		Stack<String> args = new Stack<String>();
		Map<String, String> variables = new LinkedHashMap<String, String>();
		Stack<Boolean> areLetLastOps = new Stack<Boolean>();

		String[] tokens = getTokens(expression);
		for (String token : tokens) {
			if (isNumeric(token)) {
				if (isLetLastOperator(areLetLastOps) && isVariableLastParam(values)) {
					variables.put(values.peek(), token);
				}

				values.push(token);
			} else if (isVariable(token)) {
				if (variables.containsKey(token)) {
					values.push(variables.get(token));
				} else {
					values.push(token);
				}
			} else if (isLeftBracket(token)) {
				operators.push(token);
			} else if (isRightBracket(token)) {

				while (!operators.empty() && !isLeftBracket(operators.peek())) {
					addArgument(values, args);
					String op = operators.pop();
					if (!isComma(op)) {
						throw new InvalidExpressionException("A comma should separate the function arguments");
					}
				}
				addArgument(values, args);

				if (operators.empty()) {
					throw new InvalidExpressionException("Expression is missing a left bracket");
				} else {
					operators.pop(); // discard the left bracket
				}

				if (operators.empty()) {
					throw new InvalidExpressionException("Missing function name");
				} else {
					String op = operators.pop();
					areLetLastOps.pop();

					if (isBinaryOperator(op)) {
						String result = evaluateBinaryOp(op, args);

						// updated variables if previous op is LET and this BINARY op is its second arg
						if (isLetLastOperator(areLetLastOps) && isVariableLastParam(values)) {
							variables.put(values.peek(), result);
						}

						values.push(result);
					} else if (isLetOperator(op)) {
						if (args.size() != 3) {
							throw new InvalidExpressionException(
									"LET operation accepts exactly 3 arguments, but args=" + args);
						}
						variables.remove(args.pop()); // discard first arg(variable) from LET
						args.pop(); // discard second arg(numeric) from LET

						// updated variables if previous op is LET and this LET op is its second arg
						if (isLetLastOperator(areLetLastOps) && isVariableLastParam(values)) {
							variables.put(values.peek(), args.peek());
						}

						addArgument(args, values);
					} else {
						throw new InvalidExpressionException("A function name was expected, but found " + op);
					}

				}

			} else if (isOperator(token)) {
				operators.push(token);
				areLetLastOps.push(isLetOperator(token));
			} else if (isComma(token)) {
				operators.push(token);
			} else if (isWhitespace(token)) {
				continue;
			} else {
				throw new IllegalNameException("Invalid token: " + token);
			}
		}

		if (!operators.empty() || !variables.isEmpty()) {
			throw new InvalidExpressionException(
					"A closing bracket might be missing, ops=" + operators + ",vars=" + variables);
		}

		if (isVariable(values.peek())) {
			throw new InvalidExpressionException("Possible undefined variable: " + values.peek());
		}

		return Double.valueOf(values.pop());
	}

	private void addArgument(Stack<String> values, Stack<String> args) throws InvalidExpressionException {
		if (!values.empty()) {
			args.push(values.pop());
		} else {
			throw new InvalidExpressionException("Too many commas were added as separators for function arguments");
		}
	}

	boolean isVariableLastParam(Stack<String> values) {
		return !values.empty() && isVariable(values.peek());
	}

	boolean isLetLastOperator(Stack<Boolean> areLetLastOps) {
		return !areLetLastOps.empty() && areLetLastOps.peek();
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

	String evaluateBinaryOp(String op, Stack<String> args)
			throws InvalidExpressionException, InvalidEvaluatorStateException {
		if (args.size() != 2) {
			throw new InvalidExpressionException("Binary operators accept exactly 2 operands, but args=" + args);
		}
		try {
			Double num1 = Double.valueOf(args.pop());
			Double num2 = Double.valueOf(args.pop());
			Double ret = OperatorUtils.evaluateBinaryOp(op, num1, num2);
			return ret.toString();
		} catch (Exception e) {
			throw new InvalidEvaluatorStateException(
					"Issues evaluating expression having operator " + op + " and arguments " + args, e);
		}
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
