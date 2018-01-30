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
			Thread.sleep(1500);//����ʱ��
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
			//��֪Ϊʲô�д�ӡ���ʱ������volatiole�߳�Ҳ��ʵʱȥ�����ж�ȡ���µ�����ֵ
		}
		System.out.println("waiting time : " + (System.currentTimeMillis() - begin));
		exercise.gohome();
	}
}