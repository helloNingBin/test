/**
 * 
 */
package main.java.com.github.xjs.util;

import java.util.concurrent.DelayQueue;
import java.util.logging.Logger;

/**
 * @author Administrator
 *
 */
public class DelayService {

    
    private static final Logger log = Logger.getLogger(DelayService.class);  
      
    @Autowired  
    ConfigService configService;  
      
    private boolean start ;    
    private OnDelayedListener listener;  
    private DelayQueue<DSHOrder> delayQueue = new DelayQueue<DSHOrder>();  
      
    public static interface OnDelayedListener{  
        public void onDelayedArrived(DSHOrder order);  
    }  
  
    public void start(OnDelayedListener listener){  
        if(start){  
            return;  
        }  
        log.error("DelayService 启动");  
        start = true;  
        this.listener = listener;  
        new Thread(new Runnable(){  
            public void run(){  
                try{  
                    while(true){  
                        DSHOrder order = delayQueue.take();  
                        if(DelayService.this.listener != null){  
                            DelayService.this.listener.onDelayedArrived(order);  
                        }  
                    }  
                }catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }).start();;  
    }  
      
    public void add(DSHOrder order){  
        delayQueue.put(order);  
    }  
  
    public boolean remove(DSHOrder order){  
        return delayQueue.remove(order);  
    }  
      
    public void add(long orderId){  
        delayQueue.put(new DSHOrder(orderId, configService.getDshTimeOut()));  
    }  
      
    public void remove(long orderId){  
        DSHOrder[] array = delayQueue.toArray(new DSHOrder[]{});  
        if(array == null || array.length <= 0){  
            return;  
        }  
        DSHOrder target = null;  
        for(DSHOrder order : array){  
            if(order.getOrderId() == orderId){  
                target = order;  
                break;  
            }  
        }  
        if(target != null){  
            delayQueue.remove(target);  
        }  
    }  
}
