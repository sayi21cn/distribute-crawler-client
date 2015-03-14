package xu.main.java.distribute_crawler_client.task;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import xu.main.java.distribute_crawler_common.vo.TemplateContentVO;

/**
 * 任务实体
 * @author xu
 * 
 */

public class Task {

	private int taskId = 0;

	private String taskName;

	private int threadNum;

	private int urlCount = 0;

	private int alreadyCrawledUrlNum = 0;

	private String charset = "utf-8";

	private String insertDbTableName;
	/* 解析模板 */
	private TemplateContentVO templateContentVO;

	/* 完成进度 0-100 计算方法: alreadyCrawledUrlNum/urlCount 取整 */
	private int speedProgress = 0;

	private Queue<String> urlQueue = new LinkedBlockingDeque<String>();

	private Queue<String> resultInsertSqlQueue = new LinkedBlockingDeque<String>();

	// offer 添加一个元素并返回true 如果队列已满，则返回false
	public boolean offerUrl(String url) {
		boolean result = urlQueue.offer(url);
		if (result) {
			this.urlCount++;
		}
		return result;
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

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
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

	public TemplateContentVO getTemplateContentVO() {
		return templateContentVO;
	}

	public void setTemplateContentVO(TemplateContentVO templateContentVO) {
		this.templateContentVO = templateContentVO;
	}

	public int getSpeedProgress() {
		return speedProgress;
	}

	public void setSpeedProgress(int speedProgress) {
		this.speedProgress = speedProgress;
	}

	public Queue<String> getResultInsertSqlQueue() {
		return resultInsertSqlQueue;
	}

	public void setResultInsertSqlQueue(Queue<String> resultInsertSqlQueue) {
		this.resultInsertSqlQueue = resultInsertSqlQueue;
	}

	public String getInsertDbTableName() {
		return insertDbTableName;
	}

	public void setInsertDbTableName(String insertDbTableName) {
		this.insertDbTableName = insertDbTableName;
	}

	public void setUrlQueue(Queue<String> urlQueue) {
		this.urlQueue = urlQueue;
	}

}
