package mm.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mm.calculator.evaluators.BasicCalculator;

public class CalculatorMain {

	static final Logger LOGGER = LoggerFactory.getLogger(CalculatorMain.class);

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				LOGGER.error("Please provide an expression for evaluation.");
				System.exit(-1);
			}
			
			BasicCalculator calc = new BasicCalculator();
			Double result = calc.evaluate(args[0]);

			LOGGER.info("INPUT>> " + args[0]);
			LOGGER.info("OUTPUT>> " + result);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.exit(1);
		}
	}

}
