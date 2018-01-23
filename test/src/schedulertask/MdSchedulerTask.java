package schedulertask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


/**
 *计算下次执行时间
 */
public class MdSchedulerTask implements ScheduleIterator {

    private  Calendar calendarNextRunTime  =  Calendar.getInstance();  
    private String taskTriggerType =null;
    private int intervalMinute = 0;
    private int intervalSecond = 0;
    private ArrayList<String> listSchedulerTime = new ArrayList<String> ();
    private boolean isFirstRun = true;
    private String taskId = null, taskName = null;
   

   /**
		//参数 S,M,T,B
		//B表示系统启动的时候执行并且只执行一次
		//M表示分钟，M10 每个隔10分钟执行一次
		//S表示每1秒, S30 每个30秒执行一次
		//T:表示指定时间，T12:00;23:59 表示指定的时间12:00和23:59各执行一次 
    * @author David.Zhuang 2016年1月5日
    * @param mdSchedulerTaskBean
    */
    public MdSchedulerTask(MdSchedulerTaskBean mdSchedulerTaskBean) {
    	this.taskId = mdSchedulerTaskBean.getTaskID();
    	this.taskName = mdSchedulerTaskBean.getTaskName();
   
    	//如果NextRunTime == null
    	if (null != mdSchedulerTaskBean.getNextRunTime()){
    		calendarNextRunTime.setTime(mdSchedulerTaskBean.getNextRunTime());
    	}
    		
    	taskTriggerType = mdSchedulerTaskBean.getTriggers().substring(0,1);
    	if ( taskTriggerType.equals("M") ){
    		intervalMinute = Integer.valueOf(mdSchedulerTaskBean.getTriggers().substring(1));
    	}if ( taskTriggerType.equals("S") ){
    		intervalSecond = Integer.valueOf(mdSchedulerTaskBean.getTriggers().substring(1));
    	}else if ( taskTriggerType.equals("T") ){
    		String arraySchedulerTimes[] = mdSchedulerTaskBean.getTriggers().substring(1).split(";");
    		for(String schedulerTime: arraySchedulerTimes){
    			listSchedulerTime.add(schedulerTime);
    		}
    		 Collections.sort(listSchedulerTime);
    	}else{
    		
    	}
	}


    private Calendar getNextRunTime(){
		if (taskTriggerType.equals("M")) {
			Calendar calendarNow = Calendar.getInstance();
			if (calendarNextRunTime.compareTo(calendarNow) <= 0) {
				if (isFirstRun) {
					// 如果是刚启动系统的第一次运行，那么1分钟后执行
					calendarNow.add(Calendar.MINUTE, 2);
					isFirstRun = false;
				} else {
					// 如果不是刚启动系统的第一次运行，那么是当前时间加上间隔分钟
					calendarNow.add(Calendar.MINUTE, intervalMinute);
				}
				return calendarNow;
			} else {
				return calendarNextRunTime;
			}
		}if (taskTriggerType.equals("S")) {
			Calendar calendarNow = Calendar.getInstance();
			if (calendarNextRunTime.compareTo(calendarNow) <= 0) {
				if (isFirstRun) {
					// 如果是刚启动系统的第一次运行，那么30秒后执行
					calendarNow.add(Calendar.SECOND, 30);
					isFirstRun = false;
				} else {
					// 如果不是刚启动系统的第一次运行，那么是当前时间加上间隔时间
					calendarNow.add(Calendar.SECOND, intervalSecond);
				}
				return calendarNow;
			} else {
				return calendarNextRunTime;
			}
		}else if ( taskTriggerType.equals("T") ){
			Calendar calendarNow = Calendar.getInstance();
			Calendar calendarTmp = Calendar.getInstance();
			calendarNow.set(Calendar.SECOND, 0);
			calendarTmp.set(Calendar.SECOND, 0);
			int hour = 0 , minute = 0 ;
    		for(int i = 0; i<listSchedulerTime.size(); i++){
    			hour = Integer.valueOf( listSchedulerTime.get(i).substring( 0,2));
    			minute = Integer.valueOf( listSchedulerTime.get(i).substring( 3,5));
    			calendarTmp.set(Calendar.HOUR_OF_DAY, hour);
    			calendarTmp.set(Calendar.MINUTE, minute);
    			if (calendarTmp.compareTo(calendarNow)>0) return calendarTmp;
    		}
    		hour = Integer.valueOf( listSchedulerTime.get(0).substring( 0,2));
			minute = Integer.valueOf( listSchedulerTime.get(0).substring( 3,5 ));
			calendarTmp.add(Calendar.DATE, 1);
			calendarTmp.set(Calendar.HOUR_OF_DAY, hour);
			calendarTmp.set(Calendar.MINUTE, minute);
    		return calendarTmp;
    	}else{
    		Calendar calendarNow = Calendar.getInstance();
    		calendarNow.add(Calendar.MINUTE, 1);
    		return calendarNow;
    	}
    }

    @Override
	public Date next()
    {
		String sql= "UPDATE XF_MDSCHEDULERTASK SET XF_NEXTRUNTIME = ? WHERE XF_TASKID = ? ";
/*		try {
			DatabaseHelper.sqlExecuteUpdate(Global.gConnectionPool, sql, DatabaseHelper.SHOW_ERROR,  new Object[]{ getNextRunTime().getTime() ,  taskId });
		} catch (DbException e) {
			e.printStackTrace();
		}*/
        return getNextRunTime().getTime();
    }


	@Override
	public String getTaskID() {
		return 	this.taskId ;
	}


	@Override
	public String getTaskName() {
		return 	this.taskName ;
	}

}

