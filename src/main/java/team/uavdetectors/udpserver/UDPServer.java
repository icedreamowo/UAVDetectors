package team.uavdetectors.udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.apache.log4j.Logger;
import team.uavdetectors.factory.Factory;
import team.uavdetectors.factory.ThreadPoolFactory;
import team.uavdetectors.pojo.BaseStationData;
import team.uavdetectors.snapshot.LatestBSD;

public class UDPServer {
	private Logger log = Logger.getLogger(UDPServer.class);
	
	private UDPServerKeeper serverKeeper = null;
	private String strData = null;
	private BaseStationData bsData = null;
	private oneServer server= null;
	private int port = 0;
	private int dataSize = 2048;
	
	private void init(int port) {
		this.port = port;
		server = new oneServer(port);
		serverKeeper = Factory.getNewUDPServerKeeper(this);
	}
	public UDPServer(int port){
		init(port);
	}
	public UDPServer(int port,boolean blnCheckSelf){
		init(port);
		if(blnCheckSelf) {
			serverKeeper.checkSelf();
		}
	}
	
	public UDPServerKeeper getServerKeeper() {
		return serverKeeper;
	}
	public int getPort() {
		return port;
	}
	public void start(){
		try{
			log.debug("Try to start");
			ThreadPoolFactory.execute(server);
			log.debug("Online(using port:" + server.port + ")");
		}catch(Exception e){
			log.debug("Start Failure");
			log.debug(e.getMessage(), e);
		}
	}
	public void close(){
		try{
			log.debug("Try to exit");
			server.close();
			log.debug("Offline");
		}catch(Exception e){
			log.debug("Exit Failure");
			log.debug(e.getMessage(), e);
		}
	}
	
	class oneServer implements Runnable{
		private int port = 0;					//初始为0 用于未设置时的报错 可改进
		private DatagramSocket server = null;
		private DatagramPacket packet = null;
		private byte[] data = null;
		private byte[] newData = null;
		
		oneServer(int port){
			try{
				this.port = port;
				server = new DatagramSocket(port);
			}catch(Exception e){
				log.debug(e.getMessage(), e);
			}
		}
		public void run(){
			try{
				while(!Thread.interrupted()){
					data = new byte[dataSize];
					packet = new DatagramPacket(data, data.length);
					server.receive(packet);
					newData = new byte[packet.getLength()];
					System.arraycopy(data,0,newData,0,packet.getLength());
					strData = new String(newData);
					log.debug("data:"+ strData);
					log.debug("length:"+ strData.length());
					
					bsData = Factory.getNewBSD().setFromUDPServer(strData);
					LatestBSD.add(bsData);
				}
			}catch(Exception e){
				log.debug(e.getMessage(), e);
			}
		}
		
		public void close() throws Exception{
			server.close();
		}
	}
}