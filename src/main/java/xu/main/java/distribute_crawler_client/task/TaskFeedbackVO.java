package xu.main.java.distribute_crawler_client.task;

import java.util.List;

public class TaskFeedbackVO {

	private int taskId = 0;

	private String taskName;

	private int speedProgress = 0;

	private List<String> insertSqlList;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getSpeedProgress() {
		return speedProgress;
	}

	public void setSpeedProgress(int speedProgress) {
		this.speedProgress = speedProgress;
	}

	public List<String> getInsertSqlList() {
		return insertSqlList;
	}

	public void setInsertSqlList(List<String> insertSqlList) {
		this.insertSqlList = insertSqlList;
	}

}