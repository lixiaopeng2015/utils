package main.java.com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * Email: lixiaopeng913@sina.com
 * CreateTime:  2015/11/30 10:53
 * ModifyUser:  lixiaopeng
 * ModifyTime:  2015/11/30 10:53
 * Class Description:
 * To change this template use File | Settings | File Templates.
 */
public class ExecutorUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorUtils.class);

    public static final String  THREAD_NAME_KEY = "threadname";

    //declare ThreadPool
    private static final ThreadPoolExecutor shutdownExecutor = new ThreadPoolExecutor(0, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(100),
            new NamedThreadFactory("Close-ExecutorService-Timer", true));

    /**
     * shutdown thread
     * @param executor
     * @return
     */
    public static boolean isShutdown(Executor executor) {
        if (executor instanceof ExecutorService) {
            if (((ExecutorService) executor).isShutdown()) {
                return true;
            }
        }
        return false;
    }

    /**
     * graceful shutdown thread
     * @param executor
     * @param timeout
     */
    public static void gracefulShutdown(Executor executor, int timeout) {
        if (!(executor instanceof ExecutorService) || isShutdown(executor)) {
            return;
        }
        final ExecutorService es = (ExecutorService) executor;
        try {
            es.shutdown(); // Disable new tasks from being submitted
        } catch (SecurityException ex2) {
            return ;
        } catch (NullPointerException ex2) {
            return ;
        }
        try {
            if(! es.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                es.shutdownNow();
            }
        } catch (InterruptedException ex) {
            es.shutdownNow();
            Thread.currentThread().interrupt();
        }
        if (!isShutdown(es)){
            newThreadToCloseExecutor(es);
        }
    }

    /**
     * shutdown thread immediately
     * @param executor
     * @param timeout 超时时间
     */
    public static void shutdownNow(Executor executor, final int timeout) {
        if (!(executor instanceof ExecutorService) || isShutdown(executor)) {
            return;
        }
        final ExecutorService es = (ExecutorService) executor;
        try {
            es.shutdownNow();
        } catch (SecurityException ex2) {
            return ;
        } catch (NullPointerException ex2) {
            return ;
        }
        try {
            es.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (!isShutdown(es)){
            newThreadToCloseExecutor(es);
        }
    }

    /**
     * Before the start of the thread closed thread actuators
     * @param es
     */
    private static void newThreadToCloseExecutor(final ExecutorService es) {
        if (!isShutdown(es)) {
            shutdownExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        for (int i=0;i<1000;i++){
                            es.shutdownNow();
                            if (es.awaitTermination(10, TimeUnit.MILLISECONDS)){
                                break;
                            }
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } catch (Throwable e) {
                        logger.warn(e.getMessage(), e);
                    } 
                }
            });
        }
    }
}