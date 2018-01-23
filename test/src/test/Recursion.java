package test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Recursion {
	// public volatile int inc = 0;
	private AtomicInteger inc = new AtomicInteger();

	public void increase() {
		// inc++;
		inc.incrementAndGet();
	}

	public static void main(String[] args) {

		EvenChecker.test(new SynchronizedEvenGenerator());
	}
}

class IntGenerator {
	private boolean canceled = false;

	public int next() {
		int[] ary = { 2, 3 };
		int len = ary.length;
		int ran = new Random().nextInt(len);
		return ary[ran];
	}

	// Allow this to be canceled:
	public void cancel() {
		canceled = true;
		try {
			Thread.sleep(51);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isCanceled() {
		return canceled;
	}
}

class EvenChecker implements Runnable {
	private IntGenerator generator;
	private final int id;

	public EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
	}

	public void run() {
		while (!generator.isCanceled()) {
			int val = generator.next();
			if (val % 2 != 0) {
				System.out.println("id:" + id + ";" + val + " not even!");
				generator.cancel(); // Cancels all EvenCheckers
			}
		}
	}

	// Test any type of IntGenerator:
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++)
			exec.execute(new EvenChecker(gp, i));
		exec.shutdown();
	}

	// Default value for count:
	public static void test(IntGenerator gp) {
		test(gp, 10);
	}
}

// Simplifying mutexes with the synchronized keyword.
// {RunByHand}
class SynchronizedEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	public  int next() {
		++currentEvenValue;
		 // Thread.yield();Cause failure faster
		++currentEvenValue;
		return currentEvenValue;
	}

/*	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEvenGenerator());
	}*/
}