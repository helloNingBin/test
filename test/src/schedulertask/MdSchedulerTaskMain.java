package schedulertask;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.techtrans.framework.config.ConfigException;
import com.techtrans.framework.db.DbException;
import com.techtrans.vaadin.framework.Function;
import com.techtrans.vaadin.ui.app.AppConfig;
import com.techtrans.vaadin.util.DatabaseHelper;
import com.techtrans.vaadin.util.Global;
import com.vaadin.server.VaadinService;
/**
 *  Mall 的计划任务类，在系统启动到时候启动。
 *  此类使用单例模式，确保只会运行一次
 * @author David.Zhuang 2016年1月5日
 *
 */
public class MdSchedulerTaskMain {
	private static MdSchedulerTaskMain mdSchedulerTaskMain=null;
	private static AppConfig localAppConfig=null;  
	private MdSchedulerTaskMain() {

		try {
			
			//系统启动是数据库自动更新
			sqlUpgrate();
			//清道夫，每天晚上清理垃圾
			mdClearUp();
			//正常的计划任务
			mdSchedulerTask();
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}  
	
	
	
/**
 * 
 * @author David.Zhuang 2016年10月22日
 */
	private void mdSchedulerTask(){
		
		String schedulerTaskIdToRun  = Global.gConfig.get("SchedulerTaskIdToRun");
		if (null == schedulerTaskIdToRun || schedulerTaskIdToRun.trim().length() == 0) {
			return ;
		}
		
		String[] schedulerTaskIdsToRun =schedulerTaskIdToRun.split(",");
		if (schedulerTaskIdsToRun.length<=0){
			return ;
		}
		
		StringBuffer strWhere = new StringBuffer("( '' ");
		for (String taskId : schedulerTaskIdsToRun){
			strWhere.append(",'" + taskId.trim() + "'");
		}
		strWhere.append(")");
		
		String sql = " SELECT XF_TASKID,XF_TASKNAME,XF_STATUS,XF_TRIGGERS,XF_EXECCLASS,XF_EXECATTRIB,XF_NEXTRUNTIME "
				+ "FROM XF_MDSCHEDULERTASK WHERE XF_STATUS='01' and XF_TASKID IN  "+ strWhere;
		
		ArrayList<ArrayList<Object>> rows;
		try {
			
			//正常计划任务
			rows = DatabaseHelper.sqlExecuteQuery(Global.gConnectionPool , sql, DatabaseHelper.SHOW_ERROR);
			if(rows==null || rows.size()==0) return ;
			for (int i = 0 ; i < rows.size() ; i ++ ){
				MdSchedulerTaskBean mdSchedulerTaskBean = new MdSchedulerTaskBean();
				mdSchedulerTaskBean.setTaskID((String) rows.get(i).get(0));
				mdSchedulerTaskBean.setTaskName((String) rows.get(i).get(1));
				mdSchedulerTaskBean.setStatus((String) rows.get(i).get(2));
				mdSchedulerTaskBean.setTriggers((String) rows.get(i).get(3));
				mdSchedulerTaskBean.setExecClass((String) rows.get(i).get(4));
				mdSchedulerTaskBean.setExecAttrib((String) rows.get(i).get(5));
				mdSchedulerTaskBean.setNextRunTime( (Timestamp) rows.get(i).get(6));
				
				MdSchedulerTaskApp function = getMdSchedulerTaskClass(mdSchedulerTaskBean.getExecClass());
				function.init(mdSchedulerTaskBean , localAppConfig);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		
	}
	
	
	
	/**
	 * 清道夫，每天晚上自动清理系统垃圾或者。如果有其他项目有垃圾需要清除可以继承出来配置 
	 * @author David.Zhuang 2016年10月22日
	 * @throws Exception 
	 */
	private void mdClearUp() throws Exception {
		MdSchedulerTaskBean mdSchedulerTaskBean = new MdSchedulerTaskBean();
		mdSchedulerTaskBean.setTaskID("SYSTEMCLEARUP");
		mdSchedulerTaskBean.setTaskName("清理系统垃圾");
		mdSchedulerTaskBean.setStatus("01");
		mdSchedulerTaskBean.setTriggers("T04:00");
		mdSchedulerTaskBean.setExecClass("com.techtrans.vaadin.espos61.mis.mall.schedulertask.app.MdSchedulerTaskAppSysClearUp");
		mdSchedulerTaskBean.setExecAttrib("");
		mdSchedulerTaskBean.setNextRunTime( null);
		
		//定时触发
		MdSchedulerTaskApp function = getMdSchedulerTaskClass(mdSchedulerTaskBean.getExecClass());
		function.init(mdSchedulerTaskBean , localAppConfig);
		
		// 启动是触发
		mdSchedulerTaskBean.setTriggers("B");
		function.init(mdSchedulerTaskBean , localAppConfig);
	}





	/**
	 * 将sql upgrate 改成 一定执行，不需要配置 XF_MDSCHEDULERTASK 也执行
	 * @throws Exception 
	 */
	private void sqlUpgrate() throws Exception{
		//如果原来有配置的先删除，不用执行两次。
		String sql = "DELETE FROM XF_MDSCHEDULERTASK WHERE XF_EXECCLASS = 'com.techtrans.vaadin.espos61.mis.mall.schedulertask.app.MdSchedulerTaskAppSqlUpgrate' ";
		try {
			DatabaseHelper.sqlExecuteUpdate(Global.gConnectionPool,sql, DatabaseHelper.SHOW_ERROR );
		} catch (DbException e) {
			e.printStackTrace();
		}
		//
		MdSchedulerTaskBean mdSchedulerTaskBean = new MdSchedulerTaskBean();
		mdSchedulerTaskBean.setTaskID("SQLUPGRATE");
		mdSchedulerTaskBean.setTaskName("数据库自动更新");
		mdSchedulerTaskBean.setStatus("01");
		mdSchedulerTaskBean.setTriggers("B");
		mdSchedulerTaskBean.setExecClass("com.techtrans.vaadin.espos61.mis.mall.schedulertask.app.MdSchedulerTaskAppSqlUpgrate");
		mdSchedulerTaskBean.setExecAttrib("");
		mdSchedulerTaskBean.setNextRunTime( null);
		
		MdSchedulerTaskApp function = getMdSchedulerTaskClass(mdSchedulerTaskBean.getExecClass());
		function.init(mdSchedulerTaskBean , localAppConfig);
	}
	
	
	
	//David.Zhuang 使用单例模式确保 这个类只运行一次 
    public static MdSchedulerTaskMain getMdSchedulerTaskMain() {  
        if (mdSchedulerTaskMain == null) {    
            synchronized (MdSchedulerTaskMain.class) {    
               if (mdSchedulerTaskMain == null) {    
            	   localAppConfig = createConfig();
            	   mdSchedulerTaskMain = new MdSchedulerTaskMain();   
               }    
            }    
        }    
        return mdSchedulerTaskMain;   
    }  
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected MdSchedulerTaskApp getMdSchedulerTaskClass(String functionClass) throws Exception {
		if (functionClass == null) {
			return null;
		}
		Class<? extends Function> execClass = (Class<? extends Function>) Class.forName(functionClass);
		MdSchedulerTaskApp function = (MdSchedulerTaskApp) execClass.newInstance();
		return function;
	}
	
	protected static AppConfig createConfig() {
		AppConfig appConfig = null;
		try {
			appConfig = new AppConfig(VaadinService.getCurrent().getBaseDirectory().getParent() + "/" + VaadinService.getCurrent().getBaseDirectory().getName() + "-config.properties");
		} catch (ConfigException localConfigException) {
//			this.notify.fatal("App Error", "Cannot create config: " + localConfigException, localConfigException);
			localConfigException.printStackTrace();
		}
		return appConfig;
	}
}
