package xu.main.java.distribute_crawler_client.task;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.JobTracker;

public class TaskQuery {

	private Logger logger = Logger.getLogger(TaskQuery.class);

	private JobTracker jobTracker = new JobTracker();

	
	public Task queryTask() {
		Task task = jobTracker.queryTask();
		if (task.getTaskId() == 0) {
			return task;
		}
		if (null != TaskSpeedFeedback.TASK_MAP.get(task.getTaskId())) {
			logger.warn(String.format("TaskQuery: Task Already exist! id [%s],task_name [%s]", task.getTaskId(), task.getTaskName()));
			task.setTaskId(0);
			return task;
		}

		TaskSpeedFeedback.TASK_MAP.put(task.getTaskId(), task);
		return task;
	}
}
