package schedulertask;

import java.util.ArrayList;

import com.techtrans.framework.db.DbException;
import com.techtrans.framework.i18n.Language;
import com.techtrans.framework.i18n.MessagePrototype;
import com.techtrans.util.user.User;
import com.techtrans.vaadin.espos.common.MessageGlobal;
import com.techtrans.vaadin.espos61.mis.mall.client.TenantApp.AppController;
import com.techtrans.vaadin.framework.AppParameter;
import com.techtrans.vaadin.framework.FunctionParameter;
import com.techtrans.vaadin.ui.app.AbstractAppParameter;
import com.techtrans.vaadin.ui.app.AppConfig;
import com.techtrans.vaadin.util.AppConnectionPool;
import com.techtrans.vaadin.util.DatabaseHelper;
import com.techtrans.vaadin.util.Global;
import com.vaadin.data.Container.Filter;


/**
 * 此类用来给业务层继承使用，业务层只需要实现个 Process 方法，可以按数据库的配置文件定时处理。
 * @author David.Zhuang 2016年1月3日
 * 
 */
public abstract class MdSchedulerTaskApp {
	private final Scheduler scheduler = new Scheduler();// 调度器
	protected boolean processContinue = true;
	protected String taskId = null;
	protected String taskName = null;
	protected AppConfig appConfig = null;
	protected AppParameter appParameter = null;
	protected FunctionParameter functionParameter = null;

	public void init(MdSchedulerTaskBean mdSchedulerTaskBean , AppConfig appConfig) {
		this.appParameter = new MdSchedulerTaskAppParameter(appConfig);
		this.functionParameter = new MdSchedulerTaskFunctionParameter(mdSchedulerTaskBean);

		this.taskId = mdSchedulerTaskBean.getTaskID();
		this.taskName = mdSchedulerTaskBean.getTaskName();
		this.appConfig = appConfig;
		//<<
		//参数 S,M,T,B
		//B表示系统启动的时候执行并且只执行一次
		//M表示分钟，M10 每个隔10分钟执行一次
		//S表示每1秒, S30 每个30秒执行一次
		//T:表示指定时间，T12:00;23:59 表示指定的时间12:00和23:59各执行一次 
		String taskTriggerType = mdSchedulerTaskBean.getTriggers().substring(0,1);
    	if ( taskTriggerType.equals("B") ){
    		mainProcess();
    		return ;
    	}
    	
		MdSchedulerTask mdSchedulerTask = new MdSchedulerTask(mdSchedulerTaskBean);
		scheduler.schedule(new SchedulerTask() {
			public void run() {
				mainProcess();
			}
		}, mdSchedulerTask);
	}
	
	/**
	 * 主处理的方法
	 * 
	 * @author David.Zhuang 2016年1月3日
	 * @param processContinue
	 */
	private void mainProcess() {
		
		processContinue = beforeProcess();
		
		if (processContinue) {
			processContinue =  process();
		}

		
			afterProcess();
		
	}

	/**
	 * 给应用层可以重新
	 * 
	 * @author David.Zhuang 2016年1月3日
	 * @return
	 */
	protected abstract boolean beforeProcess();

	/**
	 * 给应用层可以重新
	 * 
	 * @author David.Zhuang 2016年1月3日
	 * @return
	 */
	protected abstract  boolean process();

	/**
	 * 给应用层可以重新
	 * 
	 * @author David.Zhuang 2016年1月3日
	 */
	protected  void afterProcess(){
		String sql= "UPDATE XF_MDSCHEDULERTASK SET XF_LASTRUNTIME = {fn now()} , XF_LASTRUNRESULT = ?  WHERE XF_TASKID = ? ";
		try {
			DatabaseHelper.sqlExecuteUpdate(Global.gConnectionPool,sql, DatabaseHelper.SHOW_ERROR,  new Object[]{ true==processContinue?"True":"False" , taskId });
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};


	/**
	 * 
	 * @author David.Zhuang 2016年1月27日
	 * @param strMsg
	 */
	protected void log(String strMsg){
		System.out.println(strMsg);
	}

	protected String getSysconfig(String strConfigName , String strDefaultValue){
		String sql = "SELECT XF_CONFIGVALUE FROM XF_SYSCONFIGDEF WHERE XF_CONFIGNAME = ?";
		try {
			ArrayList<ArrayList<Object>> result = DatabaseHelper.sqlExecuteQuery(Global.gConnectionPool,sql, DatabaseHelper.SHOW_ERROR , new Object[]{strConfigName});
			if (null==result) return strDefaultValue;
			if (result.size() > 0 ) {
				Object configValue = result.get(0).get(0);
				if (null!=configValue && !"".equals(configValue.toString().trim()))  {
					return configValue.toString();
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return strDefaultValue;
		
	}

	
	public class MdSchedulerTaskAppParameter extends AbstractAppParameter {
		private AppController controller;
    
		public MdSchedulerTaskAppParameter(AppConfig appConfig) {
			super();
			this.setConfig(appConfig);
			this.setConnectionPool(Global.gConnectionPool);
			User user = new User();
			user.setId("*");
			user.setLoginName("*");
			user.setLoginPassword("*");
			user.setEnabled(true);
			user.setName("*");
			this.setUser(user);
			this.setUserLanguage(Language.zh_CN.getCode());
			this.setNotify(new MdSchedulerTaskNotify());
		}

		@Override
		public void setConnectionPool(AppConnectionPool paramAppConnectionPool) {
			this.connectionPool = paramAppConnectionPool;
		}

		@Override
		public MessagePrototype getMessageGlobal() {
			if (this.msgGlobal == null) {
				this.msgGlobal = new MessageGlobal();
			}
			return this.msgGlobal;
		}

		public AppController getController() {
			return controller;
		}

		public void setController(AppController controller) {
			this.controller = controller;
		}

	}
	
	public class MdSchedulerTaskFunctionParameter implements FunctionParameter{
		private String attrib;
		private Filter navigatorFilter;
		private String id;
		private String description;
		private String name;
		private int maxOpenCount = 1;
		private boolean autoStart = false;
		private boolean isClosable = true;

		public MdSchedulerTaskFunctionParameter(MdSchedulerTaskBean mdSchedulerTaskBean) {
			this.setId(mdSchedulerTaskBean.getTaskID());
			this.setName(mdSchedulerTaskBean.getTaskName());
		}

		@Override
		public String getAttrib() {
			return this.attrib;
		}

		@Override
		public void setAttrib(String attrib) {
			this.attrib = attrib;
		}

		@Override
		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return this.id;
		}

		@Override
		public Filter getNavigatorFilter() {
			return this.navigatorFilter;
		}

		@Override
		public int getMaxOpenCount() {
			return this.maxOpenCount;
		}

		@Override
		public void setMaxOpenCount(int maxOpenCount) {
			this.maxOpenCount = maxOpenCount;
		}

		@Override
		public String getDescription() {
			return this.description;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public boolean isAutoStart() {
			return this.autoStart;
		}

		@Override
		public void setAutoStart(boolean autoStart) {
			this.autoStart = autoStart;
		}

		@Override
		public boolean isClosable() {
			return this.isClosable;
		}

		@Override
		public void setClosable(boolean isClosable) {
			this.isClosable = isClosable;
		}
		
	}
	
}