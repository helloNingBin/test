package schedulertask;

import java.util.Date;

import org.apache.log4j.Level;

import com.techtrans.vaadin.util.Notify;

@SuppressWarnings("serial")
public class MdSchedulerTaskNotify extends Notify {

	@SuppressWarnings("unused")
	@Override
	public boolean print(Type paramType, String paramString1, String paramString2, Throwable paramThrowable) {
		String str1 = " (" + paramThrowable.getMessage() + ")";
		Object localObject;
		if ((this.logger != null) && (paramType.getLogLevel() != Level.OFF)) {
			String str2 = "[" + displayDateTimeFormat.format((new Date()).getTime()) + "] " + paramType.getName() + " " + a() + " : " + paramString1 + " - " + paramString2 + str1;
			if (paramThrowable != null)
				this.logger.log(paramType.getLogLevel(), str2, paramThrowable);
			else
				this.logger.log(paramType.getLogLevel(), str2);
		}
		return true;
	}
	
	private String a() {
		String str1 = "";
		String str2 = "";
		String str3 = "";
		String str4 = "";
		String str5 = "";
		StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
		if ((arrayOfStackTraceElement != null) && (arrayOfStackTraceElement.length >= 5)) {
			str1 = arrayOfStackTraceElement[4].getClassName();
			str2 = arrayOfStackTraceElement[4].getMethodName();
			str3 = "Line " + arrayOfStackTraceElement[4].getLineNumber();
			str4 = "" + arrayOfStackTraceElement[4].getFileName();
		}
		if ((str1 != null) && (!(str1.isEmpty())))
			str5 = str5 + str1;
		if ((str2 != null) && (!(str2.isEmpty())))
			str5 = str5 + "." + str2;
		str5 = str5 + "(" + str4 + " - " + str3 + ")";
		return str5;
	}

}
