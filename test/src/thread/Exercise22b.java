/**
 * 
 */
package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *此实例刚好可以验证volatile的作用，不加volatile时，线程不会去主内存读取最新的值，会一直死循环。
 *测试时不要有打印输出，因为不知为什么有打印输出时，不加volatile线程也会实时去主存中读取最新的数据值
 */
public class Exercise22b {
    public volatile boolean state = false;
    public synchronized void toWork(){
    	System.out.println("to work..");
    }
    public synchronized void workDone(){
    	System.out.println("work done..");
    	state = true;
    	notifyAll();
    }
    public synchronized void gohome() throws InterruptedException{
    	while(!state){
    		wait();
    	}
    	System.out.println("go home by car");
    }
    public static void main(String[] args) {
    	Exercise22b exercise = new Exercise22b();
    	ExecutorService exec = Executors.newCachedThreadPool();
    	exec.execute(new Driverb(exercise));
    	exec.execute(new Workerb(exercise));
    	Thread.yield();
    	exec.shutdown();
	}
	@Override
	public String toString() {
		return "Exercise22 [state=" + state + "]";
	}
}
class Workerb implements Runnable{
	private Exercise22b exercise;
	public Workerb(Exercise22b exercise){
		this.exercise = exercise;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(100);
			exercise.toWork();
			Thread.sleep(3500);//工作时间
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exercise.workDone();
	}
}
class Driverb implements Runnable{
	private Exercise22b exercise;
	public Driverb(Exercise22b exercise){
		this.exercise = exercise;
	}
	@Override
	public void run() {
		try {
			exercise.gohome();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}