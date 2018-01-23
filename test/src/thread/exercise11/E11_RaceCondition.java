/**
 * 
 */
package thread.exercise11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 *
 */
public class E11_RaceCondition {
	public static void main(String[] args) {
//		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		Tank tank = new Tank();
		for (int i = 0; i < 10; i++)
			exec.execute(new ConsistencyChecker(tank));
		Thread.yield();
		exec.shutdown();
	}
}