package mm.calculator.utils;

import java.util.regex.Pattern;

public final class ParserUtils {

	static final Pattern REGEX_ALL_TOKENS = Pattern.compile("(?=[\\,\\s+()])|(?<=[\\,\\s+()])");
	static final Pattern REGEX_NUMERIC = Pattern.compile("[+-]?\\d*\\.?\\d+");
	static final Pattern REGEX_VARIABLE = Pattern.compile("[a-z]");
	static final Pattern REGEX_WHITESPACE = Pattern.compile("\\s+");
	static final Pattern REGEX_COMMA = Pattern.compile("\\,");
	static final Pattern REGEX_LEFT_BRACKET = Pattern.compile("\\(");
	static final Pattern REGEX_RIGHT_BRACKET = Pattern.compile("\\)");

	private ParserUtils() {
	}

	public static String[] getTokens(String expression) {
		return REGEX_ALL_TOKENS.split(expression);
	}

	public static boolean isNumeric(String token) {
		return REGEX_NUMERIC.matcher(token).matches();
	}

	public static boolean isVariable(String token) {
		return REGEX_VARIABLE.matcher(token).matches();
	}

	public static boolean isWhitespace(String token) {
		return REGEX_WHITESPACE.matcher(token).matches();
	}

	public static boolean isComma(String token) {
		return REGEX_COMMA.matcher(token).matches();
	}

	public static boolean isRightBracket(String token) {
		return REGEX_RIGHT_BRACKET.matcher(token).matches();
	}

	public static boolean isLeftBracket(String token) {
		return REGEX_LEFT_BRACKET.matcher(token).matches();
	}

}
