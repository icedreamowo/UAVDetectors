package team.uavdetectors.snapshot;

import org.apache.log4j.Logger;
import java.util.concurrent.ConcurrentHashMap;

import team.uavdetectors.factory.ThreadPoolFactory;
import team.uavdetectors.pojo.CoordinateData;
import team.uavdetectors.pojo.RugularBackupData;

public class LatestCD {
	private static Logger log = Logger.getLogger(LatestCD.class);
	
	private static String projectName = null;
	private static ConcurrentHashMap<String, CoordinateData> relayMap = null;//线程安全的映射 考虑后不再对map的读写使用锁
	private static ConcurrentHashMap<String, CoordinateData> convergenceMap = null;
	private static ConcurrentHashMap<String, CoordinateData> labelMap = null;
	
	private static DataStorageRunnable storageRunnable = null;
	
	private static boolean blnInitialize = false;
	
	public static boolean init(String projectName,long storageTime) {
		if(isInitialized()) {
			log.debug("LatestCD is initialized");
			return false;
		} else {
			LatestCD.projectName = projectName;
			relayMap = new ConcurrentHashMap<String, CoordinateData>();
			convergenceMap = new ConcurrentHashMap<String, CoordinateData>();
			labelMap = new ConcurrentHashMap<String, CoordinateData>();
			
			storageRunnable = new DataStorageRunnable(relayMap, convergenceMap, labelMap, storageTime);
			ThreadPoolFactory.execute(storageRunnable);
			
			//在系统刚启动时获取数据库的最新数据
			
			blnInitialize = true;
			return true;
		}
	}
	
	public static boolean isInitialized() {
		return blnInitialize;
	}
	public static String getProjectName() {
		return projectName;
	}
	public static RugularBackupData getRBD() {
		return storageRunnable.getRBD();
	}
	
	public static void update(CoordinateData coordinateData) {
		if(isInitialized()) {
			switch (coordinateData.getStationLabeltype()) {
			case CoordinateData.Relay:
				relayMap.put(coordinateData.getTargetName(), coordinateData);
				break;
			case CoordinateData.Convergence:
				convergenceMap.put(coordinateData.getTargetName(), coordinateData);
				break;
			case CoordinateData.Label:
				labelMap.put(coordinateData.getTargetName(), coordinateData);
				break;
			default:
				log.debug("Spotted illegal CoordinateDataType:" + coordinateData.getStationLabeltype());
				break;
			}
		} else {
			log.debug("LatesetCD is UNinitialized");
		}
	}
}
