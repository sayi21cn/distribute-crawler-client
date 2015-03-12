package xu.main.java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import xu.main.java.model.CrawlerTask;

public class JobTracker {
	
	

	private static final BlockingQueue<CrawlerTask> crawlerTaskQueue = new LinkedBlockingQueue<CrawlerTask>(10);

	public static CrawlerTask obtainTask() throws InterruptedException {
		return crawlerTaskQueue.take();
	}

	public static void addTask(CrawlerTask crawlerTask) throws InterruptedException {
		crawlerTaskQueue.put(crawlerTask);
	}

}
