package xu.main.java.distribute_crawler_client.task;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_common.nio_data.TaskVO;

public class TaskQuery {

	private Logger logger = Logger.getLogger(TaskQuery.class);

	public TaskVO queryTask() {
		TaskVO taskVO = TaskCenter.pollTaskFromWaitQueue();
		if (null == taskVO || taskVO.getTaskId() == 0) {
			return taskVO;
		}
		if (null != TaskSpeedFeedback.TASK_MAP.get(taskVO.getTaskId())) {
			logger.warn(String.format("TaskQuery: Task Already exist! id [%s],task_name [%s]", taskVO.getTaskId(), taskVO.getTaskName()));
			taskVO.setTaskId(0);
			return taskVO;
		}

		TaskSpeedFeedback.TASK_MAP.put(taskVO.getTaskId(), taskVO);
		return taskVO;
	}
}
