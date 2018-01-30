/**
 * 
 */
package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *��ʵ���պÿ�����֤volatile�����ã�����volatileʱ���̲߳���ȥ���ڴ��ȡ���µ�ֵ����һֱ��ѭ����
 *����ʱ��Ҫ�д�ӡ�������Ϊ��֪Ϊʲô�д�ӡ���ʱ������volatile�߳�Ҳ��ʵʱȥ�����ж�ȡ���µ�����ֵ
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
			Thread.sleep(3500);//����ʱ��
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