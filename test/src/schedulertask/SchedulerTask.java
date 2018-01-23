package schedulertask;
import java.util.TimerTask;
/**
 *继承了Runnable接口，执行processMain主逻辑
 */
public abstract class SchedulerTask implements Runnable {
    final Object lock = new Object();
    int state = VIRGIN;//任务状态
    static final int VIRGIN = 0;
    static final int SCHEDULED = 1;
    static final int CANCELLED = 2;
    TimerTask timerTask;//底层的定时器任务
    
    protected SchedulerTask() 
    {
    }
    
    public abstract void run();//调度任务执行的具体任务
    
    public boolean cancel()  {
        synchronized(lock) 
        {
            if (timerTask != null) {
                timerTask.cancel();//取消任务
            }
            boolean result = (state == SCHEDULED);//任务已经被调度执行
            state = CANCELLED;//设置任务状态为“取消”
            return result;
        }
    }
    public long scheduledExecutionTime()
    {
        synchronized(lock) 
        {
            return timerTask == null ? 0 : timerTask.scheduledExecutionTime();//任务执行时间
        }
    }

}
