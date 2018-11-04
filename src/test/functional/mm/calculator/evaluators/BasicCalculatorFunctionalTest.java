package mm.calculator.evaluators;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mm.calculator.exceptions.IllegalNameException;
import mm.calculator.exceptions.InvalidExpressionException;

public class BasicCalculatorFunctionalTest {

	BasicCalculator calc;

	@Before
	public void setup() {
		calc = new BasicCalculator();
	}

	@After
	public void destroy() {

	}

	@Test
	public void testAddNumeralsShouldSucceed() throws Exception {
		double result = calc.evaluate("add(1,2)");
		assertEquals(3, result, 0);
	}

	@Test
	public void testSubstractNumeralsShouldSucceed() throws Exception {
		double result = calc.evaluate("sub(1,2)");
		assertEquals(-1, result, 0);
	}

	@Test
	public void testMultiplyNumeralsShouldSucceed() throws Exception {
		double result = calc.evaluate("mult(1,2)");
		assertEquals(2, result, 0);
	}

	@Test
	public void testDivideNumeralsShouldSucceed() throws Exception {
		double result = calc.evaluate("div(1,2)");
		assertEquals(0.5, result, 0);
	}

	@Test
	public void testAddNumeralToSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("add(1,mult(2,3))");
		assertEquals(7, result, 0);
	}

	@Test
	public void testSubstractNumeralFromSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("sub(1,add(1,2))");
		assertEquals(-2, result, 0);
	}

	@Test
	public void testMultiplyNumeralWithSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("mult(1,div(1,2))");
		assertEquals(0.5, result, 0);
	}

	@Test
	public void testDivideNumeralToSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("div(1,mult(2,1))");
		assertEquals(0.5, result, 0);
	}

	@Test
	public void testAddSimpleExpressionToSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("add(add(2,3),mult(2,3))");
		assertEquals(11, result, 0);
	}

	@Test
	public void testSubstractSimpleExpressionFromSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("sub(mult(1,2),add(1,2))");
		assertEquals(-1, result, 0);
	}

	@Test
	public void testMultiplySimpleExpressionWithSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("mult(add(2,2),div(9,3))");
		assertEquals(12, result, 0);
	}

	@Test
	public void testDivideSimpleExpressionToSimpleExpressionShouldSucceed() throws Exception {
		double result = calc.evaluate("div(div(1,2),mult(2,1))");
		assertEquals(0.25, result, 0);
	}

	@Test
	public void testAddTripleEmbeddedShouldSucceed() throws Exception {
		double result = calc.evaluate("add(add(2,3),mult(2,div(9,3)))");
		assertEquals(11, result, 0);
	}

	@Test
	public void testSubstractTripleEmbeddedShouldSucceed() throws Exception {
		double result = calc.evaluate("sub(mult(1,div(4,2)),add(1,2))");
		assertEquals(-1, result, 0);
	}

	@Test
	public void testMultiplyTripleEmbeddedShouldSucceed() throws Exception {
		double result = calc.evaluate("mult(add(mult(2,1),2),div(9,3))");
		assertEquals(12, result, 0);
	}

	@Test
	public void testDivideTripleEmbeddedShouldSucceed() throws Exception {
		double result = calc.evaluate("div(div(sub(1,0),2),mult(2,1))");
		assertEquals(0.25, result, 0);
	}

	@Test
	public void testBasicLet() throws Exception {
		double result = calc.evaluate("let(a,5,add(a,a))");
		assertEquals(10, result, 0);
	}

	@Test
	public void testDoubleLetWithSimpleOps() throws Exception {
		double result = calc.evaluate("let(a,5,let(b,mult(a,10),add(b,a)))");
		assertEquals(55, result, 0);
	}

	@Test
	public void testTripleLetWithSimpleOps() throws Exception {
		double result = calc.evaluate("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))");
		assertEquals(40, result, 0);
	}

	@Test
	public void testTripleLetWithSimpleOps2() throws Exception {
		double result = calc.evaluate("let(a, let(b, 10, add(b, b)), let(b, 21, sub(b, a)))");
		assertEquals(1, result, 0);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testMissingBracketShoudlFail() throws Exception {
		calc.evaluate("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))");
		fail("Should have thrown exception as a closing bracket is missing at the end");
	}

	@Test(expected = IllegalNameException.class)
	public void testForMultipleLettersVariablesShouldFail() throws Exception {
		calc.evaluate("let(ab,5,add(a,a))");
		fail("Should have thrown an exception as ab is an invalid variable");
	}

	@Test(expected = IllegalNameException.class)
	public void testInvalidOperatorShouldFail() throws Exception {
		calc.evaluate("let(a,5,let(b,mul(a,10),add(b,a)))");
		fail("Should have thrown an exception as mul operator is not defined");
	}

	@Test(expected = IllegalNameException.class)
	public void testInvalidSeparatorShouldFail() throws Exception {
		calc.evaluate("let(a, let(b, 10, add(b, b)); let(b, 20, add(a, b))");
		fail("Should have thrown an exception as ; separator is not defined");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testInvalidVariable() throws Exception {
		calc.evaluate("let(a,10,b)");
		fail("Should have failed for invalid expression");
	}
	
	@Test(expected = InvalidExpressionException.class)
	public void testInvalidLetArgsNo() throws Exception {
		calc.evaluate("let(a,10)");
		fail("Should have failed for invalid expression");
	}
	
	@Test(expected = InvalidExpressionException.class)
	public void testInvalidBinaryOpArgsNo() throws Exception {
		calc.evaluate("add(1,2,3)");
		fail("Should have failed for invalid expression");
	}

	////////////////// CODE REVIEW #1////////////////////////////

	@Test
	public void testLetReturnsVariable() throws Exception {
		double result = calc.evaluate("let(a,2,a)");
		assertEquals(2, result, 0);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testExtraCommas() throws Exception {
		calc.evaluate("add(1,,,2)");
		fail("Should have failed for too many commas");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testExtraCommasAndExtraArgs() throws Exception {
		calc.evaluate("add(1,,2,,3)");
		fail("Should have failed for too many commas and args");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testInvalidExpr1() throws Exception {
		calc.evaluate("1 2 7 add");
		fail("Should have failed for invalid expression");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testInvalidExpr2() throws Exception {
		calc.evaluate("5 1 1 add sub ) )");
		fail("Should have failed for invalid expression");
	}

	@Test(expected = InvalidExpressionException.class)
	public void testInvalidExpr3() throws Exception {
		calc.evaluate("1 1 5 add ) sub )");
		fail("Should have failed for invalid expression");
	}

	////////////////// CODE REVIEW #2////////////////////////////

}
