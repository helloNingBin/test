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
public class Exercise22 {
    public volatile boolean state = false;
    public synchronized void toWork(){
    	System.out.println("to work..");
    }
    public synchronized void workDone(){
    	System.out.println("work done..");
    	state = true;
    }
    public synchronized void gohome(){
    	System.out.println("go home by car");
    }
    public static void main(String[] args) {
    	Exercise22 exercise = new Exercise22();
    	ExecutorService exec = Executors.newCachedThreadPool();
    	exec.execute(new Driver(exercise));
    	exec.execute(new Worker(exercise));
    	Thread.yield();
    	exec.shutdown();
	}
	@Override
	public String toString() {
		return "Exercise22 [state=" + state + "]";
	}
}
class Worker implements Runnable{
	private Exercise22 exercise;
	public Worker(Exercise22 exercise){
		this.exercise = exercise;
	}
	@Override
	public void run() {
		try {
			exercise.toWork();
			Thread.sleep(1500);//工作时间
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exercise.workDone();
	}
}
class Driver implements Runnable{
	private Exercise22 exercise;
	public Driver(Exercise22 exercise){
		this.exercise = exercise;
	}
	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		while(!exercise.state){
			//不知为什么有打印输出时，不加volatiole线程也会实时去主存中读取最新的数据值
		}
		System.out.println("waiting time : " + (System.currentTimeMillis() - begin));
		exercise.gohome();
	}
}