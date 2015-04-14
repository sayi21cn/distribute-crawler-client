package xu.main.java.distribute_crawler_client.task;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.TaskTrackerConfig;
import xu.main.java.distribute_crawler_common.conn_data.TaskVO;
import xu.main.java.distribute_crawler_common.util.GsonUtil;
import xu.main.java.distribute_crawler_common.util.StringHandler;
import xu.main.java.distribute_crawler_common.util.ThreadUtil;
import xu.main.java.distribute_crawler_common.vo.HtmlPath;

/**
 * 任务创建中心
 * 
 * @author xu
 * 
 */
public class TaskTracker extends Thread {

	private Logger logger = Logger.getLogger(TaskTracker.class);

	private Queue<String> taskQueue = null;

	private Queue<String> speedQueue = null;

	private Queue<String> resultQueue = null;

	@Override
	public void run() {

		if (null == this.taskQueue) {
			logger.error("TaskTracker taskQueue NULL, return");
			return;
		}

		while (true) {
			logger.info("TaskTracker: begin queryTask");

			System.out.println(String.format("queue size : [%s], queue hash : [%s]", this.taskQueue.size(), this.taskQueue.hashCode()));

			String taskVoJson = this.taskQueue.poll();

			if (StringHandler.isNullOrEmpty(taskVoJson)) {
				logger.info("TaskTracker: no task and sleep " + TaskTrackerConfig.QUERY_TASK_INTERVAL + "ms");
				ThreadUtil.sleep(TaskTrackerConfig.QUERY_TASK_INTERVAL);
				continue;
			}

			TaskVO taskVO = null;
			try {
				taskVO = GsonUtil.fromJson(taskVoJson, TaskVO.class);
			} catch (Exception e) {
				logger.error("", e);
				continue;
			}

			// 无任务
			if (null == taskVO || taskVO.getTaskId() == 0) {

				logger.info("TaskTracker: no task and sleep " + TaskTrackerConfig.QUERY_TASK_INTERVAL + "ms");
				ThreadUtil.sleep(TaskTrackerConfig.QUERY_TASK_INTERVAL);
				continue;
			}
			logger.info(String.format("TaskTracker: query a task,id:[%s],task_name:[%s],thread_num:[%s]", taskVO.getTaskId(), taskVO.getTaskName(), taskVO.getThreadNum()));

			int threadNum = taskVO.getThreadNum();

			for (int threadIndex = 0; threadIndex < threadNum; threadIndex++) {
				TaskExecutionCenter taskExecutionCenter = new TaskExecutionCenter(taskVO);
				taskExecutionCenter.setSpeedQueue(speedQueue);
				taskExecutionCenter.setResultQueue(resultQueue);
				taskExecutionCenter.setName("TaskExecutionCenter_thread_" + threadIndex);
				taskExecutionCenter.start();
				logger.info("TaskTracker: TaskExecutionCenter_thread_" + threadIndex + " started");
			}
		}
	}

	public void setTaskQueue(Queue<String> taskQueue) {
		this.taskQueue = taskQueue;
	}

	public void setSpeedQueue(Queue<String> speedQueue) {
		this.speedQueue = speedQueue;
	}

	public void setResultQueue(Queue<String> resultQueue) {
		this.resultQueue = resultQueue;
	}

	public static void main(String[] args) {
		TaskVO task = new TaskVO();
		task.setTaskId(1);
		task.setCharset("gb2312");
		task.offerUrl("http://www.ygdy8.net/html/gndy/dyzz/list_23_1.html");
		task.setInsertDbTableName("dytt_movie_catalog");

		List<String> dirPathList = Arrays.asList(".co_content8 > ul", "table");
		List<Integer> dirIndexList = Arrays.asList(0, 0);
		List<String> pathList = Arrays.asList("tbody", "tr", "td", "b", "a");
		List<Integer> pathIndexList = Arrays.asList(0, 1, 1, 0, 0);

		List<String> detailPathList = Arrays.asList("tbody", "tr", "td", "b", "a");
		List<Integer> detailPathIndexList = Arrays.asList(0, 1, 1, 0, 0);

		HtmlPath movieTitlePath = new HtmlPath();
		movieTitlePath.setPathName("dytt_movie_title");
		movieTitlePath.setDirPathList(dirPathList);
		movieTitlePath.setDirIndexList(dirIndexList);
		movieTitlePath.setPathList(pathList);
		movieTitlePath.setPathIndexList(pathIndexList);

		HtmlPath detailUrlPath = new HtmlPath();
		detailUrlPath.setPathName("dytt_movie_detail_url");
		detailUrlPath.setDirPathList(dirPathList);
		detailUrlPath.setDirIndexList(dirIndexList);
		detailUrlPath.setPathList(detailPathList);
		detailUrlPath.setPathIndexList(detailPathIndexList);
		detailUrlPath.setAttrName("href");

		// List<HtmlPath> cssPathList = Arrays.asList(movieTitlePath,
		// detailUrlPath);
	}

}
