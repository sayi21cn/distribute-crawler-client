package xu.main.java.distribute_crawler_client.task;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.TaskTrackerConfig;

/**
 * 任务创建中心
 * 
 * @author xu
 * 
 */
public class TaskTracker extends Thread {

	private Logger logger = Logger.getLogger(TaskTracker.class);

	private TaskQuery taskQuery = new TaskQuery();

	@Override
	public void run() {
		while (true) {
			Task task = taskQuery.queryTask();

			// 无任务
			if (task.getTaskId() == 0) {
				try {
					Thread.sleep(TaskTrackerConfig.QUERY_TASK_Interval);
				} catch (InterruptedException e) {
					logger.error("TaskTrcaker thread wait occor an error!", e);
				}
				continue;
			}

			int threadNum = task.getThreadNum();
			for (int threadIndex = 0; threadIndex < threadNum; threadIndex++) {
				TaskExecutionCenter taskExecutionCenter = new TaskExecutionCenter(task);
				taskExecutionCenter.start();
			}
		}
	}

}
