package schedulertask;

import java.sql.Timestamp;

public class MdSchedulerTaskBean {
	
	String taskID = null;
	String taskName = null;
	String status = null;
	String triggers = null;
	String execClass = null;
	String execAttrib = null;
	Timestamp nextRunTime = null;
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the taskID
	 */
	public String getTaskID() {
		return taskID;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param taskID the taskID to set
	 */
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the triggers
	 */
	public String getTriggers() {
		return triggers;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param triggers the triggers to set
	 */
	public void setTriggers(String triggers) {
		this.triggers = triggers;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the execClass
	 */
	public String getExecClass() {
		return execClass;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param execClass the execClass to set
	 */
	public void setExecClass(String execClass) {
		this.execClass = execClass;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the execAttrib
	 */
	public String getExecAttrib() {
		return execAttrib;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param execAttrib the execAttrib to set
	 */
	public void setExecAttrib(String execAttrib) {
		this.execAttrib = execAttrib;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @return the nextRunTime
	 */
	public Timestamp getNextRunTime() {
		return nextRunTime;
	}
	/**
	 * @author David.Zhuang 2016年1月3日
	 * @param nextRunTime the nextRunTime to set
	 */
	public void setNextRunTime(Timestamp nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

}
