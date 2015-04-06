package xu.main.java.distribute_crawler_client;

import xu.main.java.distribute_crawler_client.config.NetConfig;
import xu.main.java.distribute_crawler_client.nio.TaskQueryNioClient;
import xu.main.java.distribute_crawler_client.task.TaskTracker;
import xu.main.java.distribute_crawler_client.udp.UdpClient;

public class CrawlerClientStart {

	// ClassLoader Path :
	// F:/github/distribute-crawler/distribute-crawler-client/target/test-classes/

	public static void main(String[] args) {

		// PropertyConfigurator.configure("etc/log4j.properties");

		// Task Query client NIO 线程启动
		TaskQueryNioClient tqClient = new TaskQueryNioClient();
		tqClient.start();

		// TaskTracker线程启动
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.start();

		// 任务进度反馈线程启动
		UdpClient speedUdpClient = new UdpClient(NetConfig.INET_SOCKET_ADDRESS, NetConfig.UDP_TASK_SPEED_FEEDBACK_SERVER_PORT);
		speedUdpClient.start();

	}

}
