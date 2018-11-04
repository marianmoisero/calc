package mm.calculator.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import mm.calculator.exceptions.InvalidExpressionException;
import mm.calculator.exceptions.InvalidOperatorException;

public class OperatorUtilsTest {

	@Test
	public void testGetOpShouldSucceed() throws InvalidOperatorException {
		OperatorUtils.getOp("add");
	}

	@Test
	public void testIsOperatorShouldSucceed() {
		assertTrue(OperatorUtils.isOperator("div"));
	}

	@Test
	public void testIsBinaryOperatorShouldSucceed() {
		assertTrue(OperatorUtils.isOperator("mult"));
	}

	@Test
	public void testIsLetOperatorShouldSucceed() {
		assertTrue(OperatorUtils.isOperator("let"));
	}

	@Test
	public void testAddShouldSucceed() throws InvalidOperatorException, InvalidExpressionException {
		Double val1 = 0.0;
		Double val2 = 2.0;
		Double result = 2.0;
		assertTrue(result.equals(OperatorUtils.evaluateBinaryOp("add", val1, val2)));
	}

	@Test
	public void testSubShouldSucceed() throws InvalidOperatorException, InvalidExpressionException {
		Double val1 = 4.0;
		Double val2 = 2.0;
		Double result = 2.0;
		assertTrue(result.equals(OperatorUtils.evaluateBinaryOp("sub", val1, val2)));
	}

	@Test
	public void testMultShouldSucceed() throws InvalidOperatorException, InvalidExpressionException {
		Double val1 = 0.0;
		Double val2 = 2.0;
		Double result = 0.0;
		assertTrue(result.equals(OperatorUtils.evaluateBinaryOp("mult", val1, val2)));
	}

	@Test
	public void testDivShouldSucceed() throws InvalidOperatorException, InvalidExpressionException {
		Double val1 = 0.0;
		Double val2 = 2.0;
		Double result = 0.0;
		assertTrue(result.equals(OperatorUtils.evaluateBinaryOp("div", val1, val2)));
	}

	@Test(expected = InvalidOperatorException.class)
	public void testGetOpShouldThrowExForUnknownOp() throws InvalidOperatorException {
		OperatorUtils.getOp("mod");
	}

	@Test
	public void testIsOperatorShouldReturnFalse() {
		assertFalse(OperatorUtils.isOperator("mod"));
	}

	@Test
	public void testIsBinaryOperatorShouldReturnFalse() {
		assertFalse(OperatorUtils.isBinaryOperator("let"));
	}

	@Test
	public void testIsLetOperatorShouldReturnFalse() {
		assertFalse(OperatorUtils.isLetOperator("mult"));
	}

	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateBinaryOpShouldThrowExForDivByZero()
			throws InvalidOperatorException, InvalidExpressionException {
		Double val1 = 2.0;
		Double val2 = 0.0;
		OperatorUtils.evaluateBinaryOp("div", val1, val2);
	}

}
