package mm.calculator.evaluators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import mm.calculator.exceptions.IllegalNameException;
import mm.calculator.exceptions.InvalidEvaluatorStateException;
import mm.calculator.exceptions.InvalidExpressionException;
import mm.calculator.exceptions.InvalidOperatorException;

public class BasicCalculatorTest {
	@Spy
	BasicCalculator testObj;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEvaluateShouldSucceed() throws Exception {
		Double result = 40.0;
		Double actual = testObj.evaluate("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))");
		assertTrue(result.equals(actual));
	}

	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateShouldFailIfClosingBracketMissing() throws Exception {
		testObj.evaluate("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))");
		fail("The test should have failed as the expression is missing ending bracket");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateShouldFailForInvalidExpr() throws Exception {
		testObj.evaluate("let(b, 20, (a, b))");
		fail("The test should have failed as the expression is invalid");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateShouldFailWhenOpsMissing() throws Exception {
		testObj.evaluate("(2, 4)");
		fail("The test should have failed as the expression is missing operators");
	}

	@Test(expected = IllegalNameException.class)
	public void testEvaluateShouldFailForUnknownOp() throws Exception {
		testObj.evaluate("let(a, let(b, let(x, 20, add(22, x)), add(b, b)), let(b, add(20,a), addi(a, b)))");
		fail("The test should have failed as the expression has invalid operator");
	}

	@Test
	public void testIsOperatorShouldSucceed() throws InvalidOperatorException {
		assertTrue(testObj.isOperator("div"));
	}

	@Test
	public void testIsBinaryOperatorShouldSucceed() throws InvalidOperatorException {
		assertTrue(testObj.isOperator("mult"));
	}

	@Test
	public void testIsLetOperatorShouldSucceed() throws InvalidOperatorException {
		assertTrue(testObj.isOperator("let"));
	}

	@Test
	public void testEvaluateBinaryOpShouldSucceed() throws InvalidExpressionException, InvalidEvaluatorStateException {
		Stack<String> args = new Stack<String>();
		args.push("0.0");
		args.push("2.0");
		String result = "2.0";
		assertTrue(result.equals(testObj.evaluateBinaryOp("add", args)));
	}

	@Test
	public void testGetTokensShouldSucceed() {
		String[] tokens = testObj.getTokens("add(-1,3)");
		assertEquals(6, tokens.length);
	}

	@Test
	public void testIsNumericShouldSucceed() {
		assertTrue(testObj.isNumeric("-13.23"));
	}

	@Test
	public void testIsVariableShouldSucceed() {
		assertTrue(testObj.isVariable("m"));
	}

	@Test
	public void testIsWhitespaceShouldSucceed() {
		assertTrue(testObj.isWhitespace(" 	"));
	}

	@Test
	public void testIsCommaShouldSucceed() {
		assertTrue(testObj.isComma(","));
	}

	@Test
	public void testIsRightBracketShouldSucceed() {
		assertTrue(testObj.isRightBracket(")"));
	}

	@Test
	public void testIsLeftBracketShouldSucceed() {
		assertTrue(testObj.isLeftBracket("("));
	}

}
