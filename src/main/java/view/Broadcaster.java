package view;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broadcaster implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    private static final LinkedList<BroadcastListener> listeners = new LinkedList<>();
    
    public interface BroadcastListener{
        void receiveBroadcast(String message);
    }

    public static synchronized void register(BroadcastListener listener){
        listeners.add(listener);
    }
    public static synchronized void unregister(BroadcastListener listener){
        listeners.remove(listener);
    }
    public static synchronized void broadcast(final String message){
        for(final BroadcastListener listener : listeners){
            executorService.execute(()->{
                listener.receiveBroadcast(message);
            });
        }
    }
}