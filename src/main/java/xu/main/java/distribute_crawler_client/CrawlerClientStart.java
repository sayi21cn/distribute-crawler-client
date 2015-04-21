package xu.main.java.distribute_crawler_client;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import xu.main.java.distribute_crawler_client.config.NetConfig;
import xu.main.java.distribute_crawler_client.nio.TaskQueryNioClient;
import xu.main.java.distribute_crawler_client.task.TaskTracker;
import xu.main.java.distribute_crawler_client.udp.UdpClient;

public class CrawlerClientStart {

	// ClassLoader Path :
	// F:/github/distribute-crawler/distribute-crawler-client/target/test-classes/

	public static void main(String[] args) {

//		PropertyConfigurator.configure("etc/log4j.properties");

		/* 服务端任务推送队列 */
		Queue<String> pushedQueue = new LinkedBlockingDeque<String>();

		/* 进度反馈队列 */
		Queue<String> speedQueue = new LinkedBlockingDeque<String>();

		/* 数据抽取结果队列 */
		Queue<String> resultQueue = new LinkedBlockingDeque<String>();
		
		
		// Task Query client NIO 线程启动
		TaskQueryNioClient tqClient = new TaskQueryNioClient();
		tqClient.setQueue(pushedQueue);
		tqClient.start();

		// TaskTracker线程启动
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setTaskQueue(pushedQueue);
		taskTracker.setSpeedQueue(speedQueue);
		taskTracker.setResultQueue(resultQueue);
		taskTracker.start();

		// 任务进度反馈线程启动
		UdpClient speedUdpClient = new UdpClient(NetConfig.INET_SOCKET_ADDRESS, NetConfig.UDP_TASK_SPEED_FEEDBACK_SERVER_PORT);
		speedUdpClient.setName("SpeedUdpClient");
		speedUdpClient.setQueue(speedQueue);
		speedUdpClient.start();
		
		// 结果反馈线程启动
		UdpClient extractResultUdpClient = new UdpClient(NetConfig.INET_SOCKET_ADDRESS, NetConfig.UDP_EXTRACT_RESULT_SERVER_PORT);
		extractResultUdpClient.setName("ExtractResultUdpClient");
		extractResultUdpClient.setQueue(resultQueue);
		extractResultUdpClient.start();

	}

}
