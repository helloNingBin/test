package schedulertask;

import javax.servlet.ServletException;

import com.techtrans.framework.helper.Helper;
import com.techtrans.helper.DefaultHelperFactory;
import com.techtrans.vaadin.framework.AppFooter;
import com.techtrans.vaadin.framework.AppHeader;
import com.techtrans.vaadin.framework.AppLayout;
import com.techtrans.vaadin.framework.AppMain;
import com.techtrans.vaadin.framework.AppMenu;
import com.techtrans.vaadin.framework.AppParameter;
import com.techtrans.vaadin.ui.app.AbstractApp;
import com.techtrans.vaadin.util.AppConnectionPool;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;

/**
 * @author David.Zhuang 2016年2月8日
 *
 */
@SuppressWarnings("serial")
public class MdSchedulerTaskVaadinServlet extends VaadinServlet {

	/* (non-Javadoc)
	 * @see com.vaadin.server.VaadinServlet#servletInitialized()
	 * @author David.Zhuang 2016年1月31日
	 */
	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		Helper.setFactory(new DefaultHelperFactory());
//		MdSchedulerTaskAppDataSynchGet abc = new MdSchedulerTaskAppDataSynchGet();
		MdSchedulerApp app = new MdSchedulerApp(null);
		app.getConfig();
		app.createConnectionPool();
		@SuppressWarnings("unused")
		MdSchedulerTaskMain mdSchedulerTaskMain =MdSchedulerTaskMain.getMdSchedulerTaskMain();
		
	}

	
	
	@SuppressWarnings("rawtypes")
	public class MdSchedulerApp extends AbstractApp{
		
		
		
		/* (non-Javadoc)
		 * @see com.techtrans.vaadin.ui.app.AbstractApp#createConnectionPool()
		 * @author David.Zhuang 2016年2月8日
		 */
		@Override
		public AppConnectionPool createConnectionPool() {
			// TODO Auto-generated method stub
			return super.createConnectionPool();
		}


		@SuppressWarnings("unchecked")
		public  void getConfig() {
			this.config =  super.createConfig();
		}

		public MdSchedulerApp(String paramString) {
			super(paramString);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initApp() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected Component createRoot() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AppParameter createParameter() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AppLayout createLayout() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AppHeader createHeader() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AppMenu createMenu() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AppMain createMain() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AppFooter createFooter() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
