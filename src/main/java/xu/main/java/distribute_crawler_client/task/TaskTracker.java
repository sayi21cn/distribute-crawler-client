package xu.main.java.distribute_crawler_client.task;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.TaskTrackerConfig;
import xu.main.java.distribute_crawler_common.util.GsonUtil;
import xu.main.java.distribute_crawler_common.vo.HtmlPath;

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
			logger.info("TaskTracker: begin queryTask");
			Task task = taskQuery.queryTask();

			// 无任务
			if (task.getTaskId() == 0) {
				try {
					logger.info("TaskTracker: no task and sleep " + TaskTrackerConfig.QUERY_TASK_INTERVAL + "ms");
					Thread.sleep(TaskTrackerConfig.QUERY_TASK_INTERVAL);
				} catch (InterruptedException e) {
					logger.error("TaskTrcaker thread wait occor an error!", e);
				}
				continue;
			}
			logger.info(String.format("TaskTracker: query a task,id:[%s],task_name:[%s],thread_num:[%s]", task.getTaskId(), task.getTaskName(), task.getThreadNum()));

			int threadNum = task.getThreadNum();

			for (int threadIndex = 0; threadIndex < threadNum; threadIndex++) {
				TaskExecutionCenter taskExecutionCenter = new TaskExecutionCenter(task);
				taskExecutionCenter.setName("TaskExecutionCenter_thread_"+threadIndex);
				taskExecutionCenter.start();
				logger.info("TaskTracker: TaskExecutionCenter_thread_"+threadIndex+" started");
			}
		}
	}

	public static void main(String[] args) {
		Task task = new Task();
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

		List<HtmlPath> cssPathList = Arrays.asList(movieTitlePath, detailUrlPath);
		System.out.println(GsonUtil.toJson(cssPathList));
	}

}
