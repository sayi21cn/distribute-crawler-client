package xu.main.java.distribute_crawler_client;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 
 * @author xu
 * 
 */
// Queue
// offer 添加一个元素并返回true 如果队列已满，则返回false
// poll 移除并返问队列头部的元素 如果队列为空，则返回null
// peek 返回队列头部的元素 如果队列为空，则返回null
// put 添加一个元素 如果队列满，则阻塞
// take 移除并返回队列头部的元素 如果队列为空，则阻塞
public class Task {

	private int taskId = 0;

	private String taskName;

	private int urlCount = 0;

	private int alreadyCrawledUrlNum = 0;

	/* 完成进度 0-100 计算方法: alreadyCrawledUrlNum/urlCount 取整 */
	private int speedProgress = 0;

	private Queue<String> urlQueue = new LinkedBlockingDeque<String>();
	
	// offer 添加一个元素并返回true 如果队列已满，则返回false
	public boolean offerUrl(String url){
		return urlQueue.offer(url);
	}
	
	// poll 移除并返问队列头部的元素 如果队列为空，则返回null
	public String pollUrl() {
		synchronized (Task.class) {
			if (alreadyCrawledUrlNum < urlCount) {
				alreadyCrawledUrlNum++;
				updateSpeedProgress();
			}
			return urlQueue.poll();
		}

	}

	private void updateSpeedProgress() {
		this.speedProgress = alreadyCrawledUrlNum * 100 / urlCount;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getUrlCount() {
		return urlCount;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
