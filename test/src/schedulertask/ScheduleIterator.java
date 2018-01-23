package schedulertask;
import java.util.Date;

/**
 * 
 * @author David.Zhuang 2016年1月5日
 *
 */
public interface ScheduleIterator 
{
    public Date next();
    public String getTaskID();
    public String getTaskName();
}