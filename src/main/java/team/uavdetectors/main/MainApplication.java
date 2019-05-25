package team.uavdetectors.main;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import team.uavdetectors.snapshot.LatestBSD;
import team.uavdetectors.snapshot.LatestCD;
import team.uavdetectors.udpserver.HardwareSimulation;
import team.uavdetectors.udpserver.UDPServer;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		PropertyConfigurator.configure("config/log4j.properties");
		
		LatestBSD.init(3);
		LatestCD.init("ZJlab. Project",2000);
		UDPServer udp = new UDPServer(28888,true);
		udp.start();
		HardwareSimulation simulation = new HardwareSimulation();
		
		
		for(;;) {						//模拟5s发送一次数据
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			simulation.send("", 28888);
		}
	}
}