package xu.main.java.distribute_crawler_client.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.NetConfig;
import xu.main.java.distribute_crawler_client.queue.TaskSpeedFeecbackClientQueue;

public class TaskSpeedUdpClient extends Thread {

	// private byte[] buff = new byte[NetConfig.UDP_DATA_LEN];

	private DatagramPacket outPacket = null;

	private Logger logger = Logger.getLogger(TaskSpeedUdpClient.class);

	@Override
	public void run() {
		logger.info(String.format("TaskSpeedUdpClient open server : [ %s ] , Port : [ %s ]", NetConfig.INET_SOCKET_ADDRESS, NetConfig.UDP_TASK_RECORD_SERVER_PORT));
		try {
			DatagramSocket socket = new DatagramSocket();
			outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(NetConfig.INET_SOCKET_ADDRESS), NetConfig.UDP_TASK_RECORD_SERVER_PORT);
			while (true) {
				try {
					String speedfeedbackJson = TaskSpeedFeecbackClientQueue.takeFeedbcak();
					System.out.println("send data:\t" + speedfeedbackJson);
					outPacket.setData(speedfeedbackJson.getBytes());
					socket.send(outPacket);
					Thread.sleep(100);
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

	public static void main(String[] args) {
		TaskSpeedUdpClient taskSpeedUdpClient = new TaskSpeedUdpClient();
		taskSpeedUdpClient.run();
	}

}
