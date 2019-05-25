package team.uavdetectors.factory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.collections.SynchronizedQueue;

public class ThreadPoolFactory {
	private static TraceThreadPoolExecutor pool = null;
	
	public static void execute(Runnable task) {
		if(pool == null) {							//newCachedThreadPool
			pool = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L,
												TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		}
		pool.execute(task);
	}
}
