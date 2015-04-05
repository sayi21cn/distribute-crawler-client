package xu.main.java.distribute_crawler_client.queue;

import java.util.concurrent.LinkedBlockingDeque;

public class TaskSpeedFeecbackClientQueue {

	private static LinkedBlockingDeque<String> speedQueue = new LinkedBlockingDeque<String>();

	public static boolean offerFeedback(String feedbackJson) {

		return speedQueue.offer(feedbackJson);
	}

	
	
	public static String takeFeedbcak() throws InterruptedException {
		return speedQueue.take();
	}

}
