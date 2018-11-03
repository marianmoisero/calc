package mm.calculator.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserUtilsTest {

	@Test
	public void testGetTokensShouldSucceed() {
		String[] tokens = ParserUtils.getTokens("add(-1,3)");
		assertEquals(6, tokens.length);
	}

	@Test
	public void testIsNumericShouldSucceed() {
		assertTrue(ParserUtils.isNumeric("-13.23"));
	}

	@Test
	public void testIsVariableShouldSucceed() {
		assertTrue(ParserUtils.isVariable("m"));
	}

	@Test
	public void testIsWhitespaceShouldSucceed() {
		assertTrue(ParserUtils.isWhitespace(" 	"));
	}

	@Test
	public void testIsCommaShouldSucceed() {
		assertTrue(ParserUtils.isComma(","));
	}

	@Test
	public void testIsRightBracketShouldSucceed() {
		assertTrue(ParserUtils.isRightBracket(")"));
	}

	@Test
	public void testIsLeftBracketShouldSucceed() {
		assertTrue(ParserUtils.isLeftBracket("("));
	}

	@Test
	public void testGetTokensShouldReturnInvalidTokens() {
		String[] tokens = ParserUtils.getTokens("3add (-2) ab");
		assertEquals(7, tokens.length);
	}

	@Test
	public void testIsNumericShouldReturnFalse() {
		assertFalse(ParserUtils.isNumeric("-13.23e-11"));
	}

	@Test
	public void testIsVariableShouldReturnFalse() {
		assertFalse(ParserUtils.isVariable("a1"));
	}

	@Test
	public void testIsWhitespaceShouldReturnFalse() {
		assertFalse(ParserUtils.isWhitespace(" \\s "));
	}

	@Test
	public void testIsCommaShouldReturnFalse() {
		assertFalse(ParserUtils.isComma(";"));
	}

	@Test
	public void testIsRightBracketShouldReturnFalse() {
		assertFalse(ParserUtils.isRightBracket("("));
	}

	@Test
	public void testIsLeftBracketShouldReturnFalse() {
		assertFalse(ParserUtils.isLeftBracket(")"));
	}

}
