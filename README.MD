# calc

Expression Evaluator

'''
mmoise@mm:~/code/calculator/calculator$ java -jar ./target/calculator.jar "add(1,2)"
11/02/2018 23:16:51.410 INFO  [main] mm.calculator.CalculatorMain INPUT>> add(1,2)
11/02/2018 23:16:51.413 INFO  [main] mm.calculator.CalculatorMain OUTPUT>> 3.0
'''

If file logging is enabled in logback.xml, please double check that the specified files can be created.
'''
touch /var/log/calculator.log
sudo chmod a+rwx /var/log/calculator.log
'''