package xu.main.java.distribute_crawler_client.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Queue;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.NetConfig;
import xu.main.java.distribute_crawler_client.queue.PortQueueClientFactory;

public class UdpClient extends Thread {

	// private byte[] buff = new byte[NetConfig.UDP_DATA_LEN];

	private DatagramPacket outPacket = null;

	private Logger logger = Logger.getLogger(UdpClient.class);

	private String serverIp = "127.0.0.1";

	private int serverPort = 5667;

	private Queue<String> queue = null;

	public UdpClient(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.queue = PortQueueClientFactory.getInstance().getQueyeByServerPort(serverPort);
		if (null == this.queue) {
			logger.error(String.format("ClientPortQueue map value is NULL by Server Port : [%s]", serverPort));
		}
	}

	@Override
	public void run() {
		if (null == this.queue) {
			logger.info("Message queue is NULL , thread return.");
			return;
		}
		logger.info(String.format("[ %s ] open server : [ %s ] , Port : [ %s ]", Thread.currentThread().getName(), serverIp, serverPort));

		try {
			DatagramSocket socket = new DatagramSocket();
			outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(serverIp), serverPort);
			while (true) {
				try {
					String speedfeedbackJson = this.queue.poll();
					if (null == speedfeedbackJson) {
						Thread.sleep(NetConfig.UDP_SPEED_FEEDBACK_INTERVAL);
						continue;
					}
					byte[] speedBytes = speedfeedbackJson.getBytes();
					System.out.println("send data:\t" + speedfeedbackJson);
					System.out.println(speedBytes.length);
					outPacket.setLength(speedBytes.length);
					outPacket.setData(speedBytes);
					socket.send(outPacket);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
