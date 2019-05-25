package team.uavdetectors.udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.log4j.Logger;

public class HardwareSimulation {
	private Logger log = Logger.getLogger(HardwareSimulation.class);
	private DatagramSocket socket = null;
	private DatagramPacket packet = null;
	
	public void send(String str,int port) {
		send(str.getBytes(), port);
	}
	public void send(byte[] bytes,int port) {
		try{
			socket = new DatagramSocket();
			packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), port);
			socket.send(packet);
		} catch(Exception e) {
			log.debug(e);
		}
	}
}
