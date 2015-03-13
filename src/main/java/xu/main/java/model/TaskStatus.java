package xu.main.java.model;

/**
 * 任务状态实体类
 * 
 * @author xu
 * 
 */
public class TaskStatus {

	private String taskName;

	private int urlCount = 0;

	private int alreadyCrawledUrlNum = 0;

	/* 完成进度 0-100 计算方法: alreadyCrawledUrlNum/urlCount 取整 */
	private int speedProgress = 0;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getUrlCount() {
		return urlCount;
	}

	public void setUrlCount(int urlCount) {
		this.urlCount = urlCount;
	}

	public int getAlreadyCrawledUrlNum() {
		return alreadyCrawledUrlNum;
	}

	public void setAlreadyCrawledUrlNum(int alreadyCrawledUrlNum) {
		this.alreadyCrawledUrlNum = alreadyCrawledUrlNum;
	}

	public int getSpeedProgress() {
		return speedProgress;
	}

	public void setSpeedProgress(int speedProgress) {
		this.speedProgress = speedProgress;
	}

}
