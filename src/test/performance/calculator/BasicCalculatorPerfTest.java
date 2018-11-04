package calculator;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import mm.calculator.evaluators.BasicCalculator;

public class BasicCalculatorPerfTest {

	static final int THREADS_COUNT = 1000;
	static final String EXPRESSION = "let(a, let(b, 10, add(b, mult(3,b))), let(b, 21, sub(b, a)))";

	@Test
	public void testExprEvalMultipleThreads() throws Exception {
		long start = System.currentTimeMillis();

		CountDownLatch readyThreadsCounter = new CountDownLatch(THREADS_COUNT);
		CountDownLatch completedThreadsCounter = new CountDownLatch(THREADS_COUNT);
		CountDownLatch threadsBlocker = new CountDownLatch(1);

		List<Thread> threads = Stream
				.generate(() -> new Thread(
						new EvaluatorThread(readyThreadsCounter, completedThreadsCounter, threadsBlocker, EXPRESSION)))
				.limit(THREADS_COUNT).collect(Collectors.toList());

		threads.forEach(Thread::start);
		readyThreadsCounter.await();
		threadsBlocker.countDown();
		completedThreadsCounter.await();

		long stop = System.currentTimeMillis();
		System.out.println(
				"It took " + (stop - start) / 1000 + " seconds to run " + THREADS_COUNT + " threads simulatneously");
	}

	class EvaluatorThread implements Runnable {
		CountDownLatch readyThreadsCounter;
		CountDownLatch completedThreadsCounter;
		CountDownLatch threadsBlocker;
		String expression;

		public EvaluatorThread(CountDownLatch readyThreadsCounter, CountDownLatch completedThreadsCounter,
				CountDownLatch threadsBlocker, String expression) {
			super();
			this.readyThreadsCounter = readyThreadsCounter;
			this.completedThreadsCounter = completedThreadsCounter;
			this.threadsBlocker = threadsBlocker;
			this.expression = expression;
		}

		public void evaluate(String expression) throws Exception {
			new BasicCalculator().evaluate(expression);
		}

		public void run() {
			readyThreadsCounter.countDown();
			try {
				threadsBlocker.await(10, TimeUnit.MINUTES);
				evaluate(expression);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				completedThreadsCounter.countDown();
			}
		}

	}

}
