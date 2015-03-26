package xu.main.java.distribute_crawler_client;

import org.apache.log4j.PropertyConfigurator;

import xu.main.java.distribute_crawler_client.db.DbTracker;
import xu.main.java.distribute_crawler_client.task.TaskSpeedFeedback;
import xu.main.java.distribute_crawler_client.task.TaskTracker;

public class CrawlerClientStart {

	// ClassLoader Path :
	// F:/github/distribute-crawler/distribute-crawler-client/target/test-classes/

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("etc/log4j.properties");

		// 数据库线程启动
		DbTracker dbTracker = new DbTracker();
		dbTracker.start();

		// JobTracker线程启动

		// TaskTracker线程启动
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.start();

		// 任务进度反馈线程启动
		TaskSpeedFeedback taskSpeedFeedback = new TaskSpeedFeedback();
		taskSpeedFeedback.start();

	}

}
