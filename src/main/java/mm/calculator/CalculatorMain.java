package mm.calculator;

import mm.calculator.evaluators.BasicCalculator;
import mm.calculator.exceptions.IllegalEvaluatorStateException;
import mm.calculator.exceptions.IllegalNameException;
import mm.calculator.exceptions.InvalidExpressionException;

public class CalculatorMain {

	public static void main(String[] args) {
		try {
			BasicCalculator calc = new BasicCalculator();

			if (args.length == 0) {
				System.err.println("Please provide an expression for evaluation.");
				System.exit(-1);
			}
			Double result = calc.evaluate(args[0]);
			
			System.out.println("INPUT>> " + args[0]);
			System.out.println("OUTPUT>> " + result);
		} catch (IllegalNameException e) {
			System.err.println(e.getMessage());
			// TODO: log err
		} catch (IllegalEvaluatorStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
