/**
 * 
 */
package test;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class QQMsg {
   private  int fromId;
   private  int toId;
   private String content;
   private Date sendDate;
public  int getFromId() {
	return fromId;
}
public void setFromId(int fromId) {
	this.fromId = fromId;
}
public int getToId() {
	return toId;
}
public void setToId(int toId) {
	this.toId = toId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Date getSendDate() {
	return sendDate;
}
public void setSendDate(Date sendDate) {
	this.sendDate = sendDate;
}
public QQMsg(int fromId, int toId, String content, Date sendDate) {
	super();
	this.fromId = fromId;
	this.toId = toId;
	this.content = content;
	this.sendDate = sendDate;
}
@Override
public String toString() {
	return "QQMsg [fromId=" + fromId + ", toId=" + toId + ", content="
			+ content + ", sendDate=" + Constant.DATEFORMAT_YYYYMMDD.format(sendDate) + "]";
}
}
