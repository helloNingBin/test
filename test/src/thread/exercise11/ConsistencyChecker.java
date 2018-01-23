/**
 * 
 */
package thread.exercise11;

import java.util.Random;

/**
 * @author Administrator
 *
 */
public class ConsistencyChecker implements Runnable {
	private static Random rnd = new Random();
	private Tank tank;

	ConsistencyChecker(Tank tank) {
		this.tank = tank;
	}

	public void run() {
		for (;;) {
			// Decide whether to fill or drain the tank
			if (rnd.nextBoolean())
				tank.fill();
			else
				tank.drain();
			tank.validate();
		}
	}
}