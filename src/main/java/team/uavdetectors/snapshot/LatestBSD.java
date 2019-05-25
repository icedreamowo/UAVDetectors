package team.uavdetectors.snapshot;

import org.apache.log4j.Logger;
import java.util.concurrent.ConcurrentLinkedQueue;

import team.uavdetectors.factory.ThreadPoolFactory;
import team.uavdetectors.pojo.BaseStationData;

public class LatestBSD {
	private static Logger log = Logger.getLogger(LatestBSD.class);
	
	private static ConcurrentLinkedQueue<BaseStationData> bsdQueue = null;	//线程安全的队列保存BSD数据 以此来加快UDPServer的处理速度
	private static int conversionThreadNumber = 0;
	private static DataConversionRunnable conversionRunnable = null;
	
	private static boolean blnInitialize = false;
	
	public static boolean init(int conversionThreadNumber) {		//此参数暂时不起作用 即只使用一个线程
		if(isInitialized()) {
			log.debug("LatestBSD is initialized");
			return false;
		} else {
			bsdQueue = new ConcurrentLinkedQueue<BaseStationData>();
			conversionRunnable = new DataConversionRunnable(bsdQueue);
			LatestBSD.conversionThreadNumber = conversionThreadNumber;
			
			for(int i = 0;i < LatestBSD.conversionThreadNumber ; i++) {
				ThreadPoolFactory.execute(conversionRunnable);
			}
			
			blnInitialize = true;
			return true;
		}
	}
		public static boolean isInitialized() {
			return blnInitialize;
		}
		public static int getConversionThreadNumber() {
			return conversionThreadNumber;
		}
		public static void add(BaseStationData baseStationData) {
			if(isInitialized()) {
				bsdQueue.add(baseStationData);
			} else {
				log.debug("LatesetBSD is UNinitialized");
			}
		}
}
