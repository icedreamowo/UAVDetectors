package team.uavdetectors.snapshot;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;
import team.uavdetectors.algorithm.Algorithm;
import team.uavdetectors.factory.Factory;
import team.uavdetectors.pojo.BaseStationData;
import team.uavdetectors.pojo.CoordinateData;

public class DataConversionRunnable implements Runnable{			//BaseStationData -> CoordinateData
	private Logger log = Logger.getLogger(DataConversionRunnable.class);
	private Algorithm algorithm = null;
	private ConcurrentLinkedQueue<BaseStationData> bsdQueue = null;
	private BaseStationData bsData = null;
	private CoordinateData cData = null;
	
	public DataConversionRunnable(ConcurrentLinkedQueue<BaseStationData> bsdQueue) {
		this.bsdQueue = bsdQueue;
	}
	private void init() {
		algorithm = Factory.getNewTOALA();
	}
	public void run() {
		init();
		while(!Thread.interrupted()) {
			if(!bsdQueue.isEmpty()) {				//不对此操作进行加锁  通过再次判断baData是否为null来防止并发问题
				bsData = bsdQueue.poll();
			}
			if(bsData != null) {
				cData = algorithm.calculate(bsData);
				LatestCD.update(cData);
			}
			bsData = null;
		}
	}
}
