package xu.main.java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import xu.main.java.distribute_crawler_common.vo.CrawlerTask;

/**
 * 
 * 任务分发，结果收集
 * @author xu
 * 
 */

public class JobTracker {
	
	

	private static final BlockingQueue<CrawlerTask> crawlerTaskQueue = new LinkedBlockingQueue<CrawlerTask>(10);

	public static CrawlerTask obtainTask() throws InterruptedException {
		return crawlerTaskQueue.take();
	}

	public static void addTask(CrawlerTask crawlerTask) throws InterruptedException {
		crawlerTaskQueue.put(crawlerTask);
	}

}
