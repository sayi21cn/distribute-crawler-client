package xu.main.java.distribute_crawler_client.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import xu.main.java.distribute_crawler_client.config.NetConfig;

/**
 * 
 * 端口号与队列对应关系工厂
 * 
 * @author xu
 * 
 */
public class PortQueueClientFactory {

	private static PortQueueClientFactory instance = null;

	private Map<Integer, Queue<String>> map = new HashMap<Integer, Queue<String>>();

	private PortQueueClientFactory() {
		init();
	}

	public static PortQueueClientFactory getInstance() {

		if (null != instance) {
			return instance;
		}

		instance = new PortQueueClientFactory();

		return instance;

	}

	public Queue<String> getQueyeByServerPort(int serverPort) {
		return map.get(serverPort);
	}

	public void putProtQueueToMap(int serverPort, Queue<String> queue) {
		map.put(serverPort, queue);
	}

	/* 进度反馈队列 */
	private Queue<String> speedQueue = new LinkedBlockingDeque<String>();

	/* 数据抽取结果队列 */
	private Queue<String> extractResultQueue = new LinkedBlockingDeque<String>();

	private void init() {
		map.put(NetConfig.UDP_SPEED_FEEDBACK_INTERVAL, speedQueue);
		map.put(NetConfig.UDP_TASK_SPEED_FEEDBACK_SERVER_PORT, extractResultQueue);
	}

}
