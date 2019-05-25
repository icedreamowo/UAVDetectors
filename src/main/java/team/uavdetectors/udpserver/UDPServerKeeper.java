package team.uavdetectors.udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

public class UDPServerKeeper {
	private Logger log = Logger.getLogger(UDPServerKeeper.class);
	
	private UDPServer server = null;
	private static final String localhost = "127.0.0.1";
	
	public UDPServerKeeper(UDPServer server){
		this.server = server;
	}
	public void checkSelf() {								//默认为本机自检  可能会存在网络问题未检测出
		checkSelf(localhost,server.getPort());
	}
	public void checkSelf(String selfIP) {
		checkSelf(selfIP,server.getPort());
	}
	public void checkSelf(String selfIP,int port) {			//阻塞型自检
		DatagramSocket socket = null;
		DatagramPacket request = null;
		InetAddress host = null;
		String str = null;
		try{
			socket = new DatagramSocket(0);
			socket.setSoTimeout(10000);
			host = InetAddress.getByName(selfIP);
			str = "Self Checking("+ selfIP + ":" + port +")...";
			request = new DatagramPacket(str.getBytes(),str.getBytes().length, host, port);
			socket.send(request);
		}catch(Exception e){
			log.debug("Self Check Failure");
			log.debug(e.getStackTrace());
		}finally {
			if(socket != null) {
				socket.close();
			}
			socket = null;
			request = null;
			host = null;
			str = null;
		}
	}
}
