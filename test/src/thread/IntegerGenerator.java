/**
 * 
 */
package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Integer����������ԭ����
 * ����һ����Ա���������Գ�ִ����������N�Σ��鿴������Ƿ�ΪN
 *
 */
public class IntegerGenerator {
   private int i = 0;
   private long time = System.currentTimeMillis();
   public IntegerGenerator(){
   }
   public void increment(){
	   while(System.currentTimeMillis() - time < (5 * 1000)){
		   
	   }
	   synchronized (this) {
		   //synchronized������ı�����������ˢ����һ���õ������̵߳�ֵ����t1��t2�̶߳���i=0��t1ִ����i++��
		   //t2�õ���ʱ��������ˢ��i��ֵ���õ�i=1��
		   this.i++;
	  }
	   for(int j = 0;j < 99000000;j++){
		   
	   }
   }
   public static void main(String[] args) {
	 int threads = 5000;
	 ExecutorService executor = Executors.newFixedThreadPool(threads);
	 IntegerGenerator generator = new IntegerGenerator();
	 for(int i = 0;i < threads;i++){
		executor.execute(new IncrementTask(generator, 10)); 
	 }
	 executor.shutdown();
	 Thread.currentThread().yield();
	 while(!executor.isTerminated()){
		 
	 }
	 System.out.println("i:" + generator.i);
}
}
class IncrementTask implements Runnable{
    private IntegerGenerator generator;
    /**
     * ��������
     */
    private int count;
    public IncrementTask(IntegerGenerator generator,int count){
    	this.generator = generator;
    	this.count = count;
    }
	@Override
	public void run() {
		for(int i = 0;i < count;i++){
			Thread.currentThread().yield();
			generator.increment();
		}
	}
	
}