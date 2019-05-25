package team.uavdetectors.snapshot;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import team.uavdetectors.dao.MongoDBDriver;
import team.uavdetectors.factory.Factory;
import team.uavdetectors.pojo.CoordinateData;
import team.uavdetectors.pojo.RugularBackupData;

public class DataStorageRunnable implements Runnable {			//只允许生成一个线程实例
	private Logger log = Logger.getLogger(DataStorageRunnable.class);
	private ConcurrentHashMap<String, CoordinateData> relayMap = null;//线程安全的映射
	private ConcurrentHashMap<String, CoordinateData> convergenceMap = null;
	private ConcurrentHashMap<String, CoordinateData> labelMap = null;
	private long storageTime = 1000;							//1000ms
	
	private RugularBackupData backup = null;
	public MongoDBDriver db = new MongoDBDriver();
	
	public DataStorageRunnable(ConcurrentHashMap<String, CoordinateData> relayMap, 
							   ConcurrentHashMap<String, CoordinateData> convergenceMap,
							   ConcurrentHashMap<String, CoordinateData> labelMap,
							   long storageTime) {
		this.relayMap = relayMap;
		this.convergenceMap = convergenceMap;
		this.labelMap = labelMap;
		this.storageTime = storageTime;
	}
	
	public RugularBackupData getRBD() {
		if(backup == null) {
			return Factory.getNewRBD();
		}else {
			return backup;
		}
	}
	
	public void run() {
		while(!Thread.interrupted()) {
			Iterator<Map.Entry<String, CoordinateData>> iterator = null;
			backup = Factory.getNewRBD();
			
			backup.setProjectName(LatestCD.getProjectName());
			iterator = relayMap.entrySet().iterator();
			while(iterator.hasNext()) {
				CoordinateData relayData = ((Map.Entry<String, CoordinateData>)iterator.next()).getValue();
				backup.addRelayList(relayData);
			}
			iterator = convergenceMap.entrySet().iterator();
			while(iterator.hasNext()) {
				CoordinateData convergenceData = ((Map.Entry<String, CoordinateData>)iterator.next()).getValue();
				backup.addRelayList(convergenceData);
			}
			iterator = labelMap.entrySet().iterator();
			while(iterator.hasNext()) {
				CoordinateData labelData = ((Map.Entry<String, CoordinateData>)iterator.next()).getValue();
				backup.addRelayList(labelData);
			}
			backup.setTimestamp(System.currentTimeMillis());
			
			iterator = null;
			db.insertRBD(backup);
			
			try {
				Thread.sleep(storageTime);
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}
	}
}
