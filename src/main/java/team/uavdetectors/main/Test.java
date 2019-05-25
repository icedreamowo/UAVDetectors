package team.uavdetectors.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import team.uavdetectors.dao.MongoDBDriver;
import team.uavdetectors.factory.Factory;
import team.uavdetectors.pojo.RugularBackupData;
import team.uavdetectors.snapshot.LatestBSD;
import team.uavdetectors.snapshot.LatestCD;
import team.uavdetectors.udpserver.HardwareSimulation;
import team.uavdetectors.udpserver.UDPServer;

public class Test {
	private static Logger log = Logger.getLogger(Test.class);
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");
		/*
		LatestBSD.init(5);
		LatestCD.init("ZJlab. Project",2000);
		UDPServer udp = new UDPServer(23456,true);
		udp.start();
		HardwareSimulation simulation = new HardwareSimulation();
		simulation.send("", 23456);
		*/
		/*
		MongoDBDriver db = new MongoDBDriver();
		db.selectAll();
		db.delete("ZJlab. Project");*/
		
		byte[] b = new byte[1];
		
		String a = null;
		try {
			a.charAt(1);
		}catch(Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
}
