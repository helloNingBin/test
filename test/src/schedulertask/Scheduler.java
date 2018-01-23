package schedulertask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.techtrans.vaadin.espos.common.CommonConstant;


/**
 *任务调度器
 */
public class Scheduler {
	private SimpleDateFormat sf=new SimpleDateFormat("yyyymmmmddddddmmmmmffffhhhhhsssssss");
	/**
	 * 执行timerTask
	 */
	private final Timer timer = new Timer();
    /**
     *继承了TimerTask，被Timer定时器执行
     */
    class SchedulerTimerTask extends TimerTask {
        private SchedulerTask schedulerTask;
        private ScheduleIterator iterator;
        public SchedulerTimerTask(SchedulerTask schedulerTask,ScheduleIterator iterator){
            this.schedulerTask = schedulerTask;
            this.iterator = iterator;
        }
        public void run()  {
            schedulerTask.run();
            reschedule(schedulerTask, iterator);
        }
    }


    public Scheduler()  {
    }
    public void cancel() {
        timer.cancel();
    }
    /**
     * 底层timer设置执行时间和执行类
     * @param schedulerTask  它来执行主逻辑
     * @param iterator       带有获取下次执行时间的方法
     */
    public void schedule(SchedulerTask schedulerTask,ScheduleIterator iterator){
		
		Date time = iterator.next();
		System.out.println("[MD Scheduler Task]["+iterator.getTaskID()+","+iterator.getTaskName()+"] : next time to run "+sf.format(time)+">>>>>>>>>>>>>>>>>>>>>>>>>>");		
		if (time == null) {
			schedulerTask.cancel();
		} else {
			synchronized (schedulerTask.lock) {
				if (schedulerTask.state != SchedulerTask.VIRGIN) {
					throw new IllegalStateException("Task already scheduled or cancelled");
				}
				schedulerTask.state = SchedulerTask.SCHEDULED;
				schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
				timer.schedule(schedulerTask.timerTask, time);
			}
		}
	}
    /**
     * 相当于重新执行一遍schedule，因为每个timerTask都只执行一次，若要重复执行，则要重复设置
     * @param schedulerTask  它来执行主逻辑
     * @param iterator       带有获取下次执行时间的方法
     */
    private void reschedule(SchedulerTask schedulerTask,ScheduleIterator iterator){
        Date time = iterator.next();
        System.out.println("[MD Scheduler Task]["+iterator.getTaskID()+","+iterator.getTaskName()+"] : next time to run "+sf.format(time)+">>>>>>>>>>>>>>>>>>>>>>>>>>");	
        if (time == null){
            schedulerTask.cancel();
        } else  {
            synchronized(schedulerTask.lock){
                if (schedulerTask.state != SchedulerTask.CANCELLED) {
                    schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
                    timer.schedule(schedulerTask.timerTask, time);
                }
            }
        }
    }
}