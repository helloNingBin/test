/**
 * 
 */
package thread.exercise11;

/**
 * @author Administrator
 *
 */
public class Tank {
	enum State {
		EMPTY, LOADED
	};

	private State state = State.EMPTY;
	private int current_load = 0;

	public void validate() {
		if ((state == State.EMPTY && current_load != 0)
				|| (state == State.LOADED && current_load == 0))
			throw new IllegalStateException();
	}

	public void fill() {
		state = State.LOADED;
		Thread.yield(); // Cause failure faster
		current_load = 10; // Arbitrary value
	}

	public void drain() {
		state = State.EMPTY;
		Thread.yield();
		current_load = 0;
	}
}