/**
 * 
 */
package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Integer自增，测试原子性
 * 定义一个成员变量，多性程执行自增方法N次，查看最后结果是否为N
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
		   //synchronized方法里的变量，会立即刷新下一个拿到锁的线程的值。如t1和t2线程都是i=0，t1执行完i++后
		   //t2得到锁时，会马上刷新i的值，得到i=1；
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
     * 自增次数
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