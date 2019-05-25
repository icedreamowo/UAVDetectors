package team.uavdetectors.factory;

import com.google.gson.Gson;

import team.uavdetectors.algorithm.TOALocationAlgorithm;
import team.uavdetectors.exception.ThreadTraceException;
import team.uavdetectors.pojo.BaseStationData;
import team.uavdetectors.pojo.CoordinateData;
import team.uavdetectors.pojo.RugularBackupData;
import team.uavdetectors.udpserver.UDPServer;
import team.uavdetectors.udpserver.UDPServerKeeper;

public class Factory {
	public static BaseStationData getNewBSD() {				//BaseStationData
		BaseStationData newBSD = new BaseStationData();
		return newBSD;
	}
	public static CoordinateData getNewCD() {				//CoordinateData
		CoordinateData newCD = new CoordinateData();
		return newCD;
	}
	public static RugularBackupData getNewRBD() {			//RugularBackupData
		RugularBackupData newRBD = new RugularBackupData();
		return newRBD;
	}
	public static Gson getNewGson() {						//Gson Tool
		Gson gson = new Gson();
		return gson;
	}
	public static UDPServerKeeper getNewUDPServerKeeper(UDPServer server) {
		UDPServerKeeper newUDPServerKeeper = new UDPServerKeeper(server);
		return newUDPServerKeeper;
	}
	public static TOALocationAlgorithm getNewTOALA() {
		TOALocationAlgorithm newTOALA = new TOALocationAlgorithm(); 
		return newTOALA;
	}
	public static ThreadTraceException getNewTTE() {
		ThreadTraceException newTTE = new ThreadTraceException();
		return newTTE;
	}
}
