package team.uavdetectors.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import team.uavdetectors.exception.ThreadTraceException;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
	private Logger log = Logger.getLogger(ThreadTraceException.class);
	
	public TraceThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,
									TimeUnit unit,BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	public void execute(Runnable task) {
		super.execute(wrap(task,Factory.getNewTTE(),Thread.currentThread().getName()));
	}
	@Override
	public Future<?> submit(Runnable task){
		return super.submit((wrap(task,Factory.getNewTTE(),Thread.currentThread().getName())));
	}
	
	private Runnable wrap(final Runnable task , final ThreadTraceException clientStack,
							String clientThreadName) {
		Runnable newRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch(Exception e) {
					log.debug(clientStack.getMessage(), clientStack);
				}
			}
		};
		return newRunnable;
	}
}
