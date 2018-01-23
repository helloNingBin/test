/**
 * 
 */
package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Constant {
	public static final SimpleDateFormat DATEFORMAT_YYYYMMDD = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final List<QQMsg> getQQData() throws ParseException{
		List<QQMsg> list = new ArrayList<QQMsg>();
		for(int i = 0;i < 30;i++){
			QQMsg msg = new QQMsg(i%2 == 0 ? 1 : 2, i%2 == 0 ? 2 : 1, "content:" + i, new Date(System.currentTimeMillis() - (3 * i * 1000)));
			list.add(msg);
		}
//		QQMsg msg1 = new QQMsg(2,1, "content:" + 1,DATEFORMAT_YYYYMMDD.parse("2017/12/18 14:18:23"));
//		QQMsg msg2 = new QQMsg(2,1, "content:" + 1,DATEFORMAT_YYYYMMDD.parse("2017/12/18 14:19:29"));
//		QQMsg msg3 = new QQMsg(2,1, "content:" + 1,DATEFORMAT_YYYYMMDD.parse("2017/12/18 14:19:43"));
//		list.add(msg1);list.add(msg2);list.add(msg3);
//		System.out.println(Recursion.isEqual(DATEFORMAT_YYYYMMDD.parse("2017/12/18 14:18:23"), DATEFORMAT_YYYYMMDD.parse("2017/12/18 14:19:29")));
		return list;
	}

}
