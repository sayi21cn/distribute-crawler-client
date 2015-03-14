package xu.main.java.distribute_crawler_client.task;

import xu.main.java.distribute_crawler_client.JobTracker;

public class TaskQuery {
	private JobTracker jobTracker = new JobTracker();

	public Task queryTask() {
		return jobTracker.queryTask();
	}
}
