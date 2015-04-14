package xu.main.java.distribute_crawler_client.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.NetConfig;
import xu.main.java.distribute_crawler_client.config.TaskTrackerConfig;
import xu.main.java.distribute_crawler_common.conn_data.SpeedFeedbackVO;
import xu.main.java.distribute_crawler_common.conn_data.TaskVO;
import xu.main.java.distribute_crawler_common.extractor.ExtractorFactory;
import xu.main.java.distribute_crawler_common.extractor.IExtractor;
import xu.main.java.distribute_crawler_common.util.GsonUtil;
import xu.main.java.distribute_crawler_common.util.HttpDownload;
import xu.main.java.distribute_crawler_common.util.StringHandler;
import xu.main.java.distribute_crawler_common.vo.HtmlPath;
import xu.main.java.distribute_crawler_common.vo.TemplateContentVO;

/**
 * 任务执行中心
 * 
 * @author xu
 * 
 */
public class TaskExecutionCenter extends Thread {

	private Logger logger = Logger.getLogger(TaskExecutionCenter.class);
	private TaskVO taskVO;
	private String charset;
	private Queue<String> speedQueue = null;
	private Queue<String> resultQueue = null;

	public TaskExecutionCenter(TaskVO taskVO) {
		this.taskVO = taskVO;
		this.charset = taskVO.getCharset();
	}

	@Override
	public void run() {

		if (null == this.speedQueue) {
			logger.info("TaskExecutionCenter speedQueue NULL ,return");
			return;
		}

		String url = null;

		while (null != (url = this.taskVO.pollUrl())) {
			logger.info("TaskExecutionCenter: " + Thread.currentThread().getName() + " start download url : " + url);
			String html = HttpDownload.download(url, charset);
			IExtractor extractor = ExtractorFactory.getInstance().getExtractor("cssExtractor");
			Map<String, String> resultMap = extractor.extractorColumns(html, taskVO.getTemplateContentVO().getHtmlPathList(), TaskTrackerConfig.SPLIT_STRING);
			resultMap.put("download_url", url);
			resultMap.put("taskId", String.valueOf(taskVO.getTaskId()));

			String result = GsonUtil.toJson(resultMap);
			this.resultQueue.offer(result);

			if (taskVO.getSpeedProgress() - taskVO.getLastSpeedFeedback() > NetConfig.UDP_SPEED_FEEDBACK_INTERVAL) {
				// 进度反馈
				SpeedFeedbackVO speedFeedbackVO = new SpeedFeedbackVO(taskVO.getTaskId(), taskVO.getSpeedProgress());
				// TODO: 后续改为存 SpeedFeedbackVO对象以减少爬虫时间消耗
				String feedbackJson = GsonUtil.toJson(speedFeedbackVO);
				this.speedQueue.offer(feedbackJson);
				taskVO.setLastSpeedFeedback(taskVO.getSpeedProgress());
			}
			// String sql = buildSaveSQL(resultMap);
			// taskVO.offerInsertSql(sql);
			// boolean result = MysqlUtil.saveToDb(conn, sql);
			// System.out.print("数据保存数据库 ");
			// System.out.println(result ? "成功" : "失败");
		}
		// taskVO.getSpeedProgress() - lastSpeedFeedback
		// >NetConfig.UDP_SPEED_FEEDBACK_INTERVAL
		if (taskVO.getAlreadyCrawledUrlNum() != taskVO.getUrlCount()) {
			this.speedQueue.offer(GsonUtil.toJson(new SpeedFeedbackVO(taskVO.getTaskId(), 101)));
			logger.error(String.format("下载线程异常结束,任务进度Id:[%s] ,任务进度:[%s]%", taskVO.getTaskId(), taskVO.getSpeedProgress()));
			return;
		}

		if (taskVO.getLastSpeedFeedback() != 100) {
			taskVO.setSpeedProgress(100);
			SpeedFeedbackVO speedFeedbackVO = new SpeedFeedbackVO(taskVO.getTaskId(), taskVO.getSpeedProgress());
			String feedbackJson = GsonUtil.toJson(speedFeedbackVO);
			this.speedQueue.offer(feedbackJson);
			taskVO.setLastSpeedFeedback(taskVO.getSpeedProgress());
		}
		logger.info(String.format("[ %s ]任务完成,Id:[%s]", Thread.currentThread().getName(), taskVO.getTaskId()));
	}

	public void setSpeedQueue(Queue<String> speedQueue) {
		this.speedQueue = speedQueue;
	}

	public void setResultQueue(Queue<String> resultQueue) {
		this.resultQueue = resultQueue;
	}

	public String buildSaveSQL(Map<String, String> resultMap) {
		StringBuffer sqlBuffer = new StringBuffer("insert into ");
		sqlBuffer.append(taskVO.getInsertDbTableName()).append(" (");
		for (HtmlPath cssPath : taskVO.getTemplateContentVO().getHtmlPathList()) {
			sqlBuffer.append(cssPath.getPathName()).append(",");
		}
		deleteBufferLast(sqlBuffer, 1);
		sqlBuffer.append(") values ('");
		for (HtmlPath cssPath : taskVO.getTemplateContentVO().getHtmlPathList()) {
			sqlBuffer.append(StringHandler.nullToEmpty(resultMap.get(cssPath.getPathName()))).append("','");
		}
		deleteBufferLast(sqlBuffer, 2);
		sqlBuffer.append(");");
		return sqlBuffer.toString();
	}

	private void deleteBufferLast(StringBuffer buffer, int deleteNum) {
		if (null == buffer || buffer.length() <= deleteNum) {
			return;
		}
		if (deleteNum == 1) {
			buffer.deleteCharAt(buffer.length() - 1);
			return;
		}
		buffer.delete(buffer.length() - deleteNum - 1, buffer.length() - 1);
	}

	public static void main(String[] args) {

		// List<String> urlList =
		// Arrays.asList("http://www.ygdy8.net/html/gndy/dyzz/index.html");
		// HtmlPath cssPath = new HtmlPath();
		// cssPath.setPathName("dytt_movie_title");
		// cssPath.setDirPath(".co-content8 > ul");
		//
		// List<HtmlPath> cssPathList = Arrays.asList(cssPath);
		// TaskTracker taskTracker = new TaskTracker(urlList, cssPathList);
		// taskTracker.run();
		/*
		 * List<String> urlList =
		 * Arrays.asList("http://zhidao.baidu.com/question/65643627.html");
		 * HtmlPath cssPath = new HtmlPath();
		 * cssPath.setPathName("baidu_known_ask");
		 * cssPath.setDirPath(".ask-title");
		 * 
		 * HtmlPath answerPath = new HtmlPath();
		 * answerPath.setPathName("baidu_known_answer");
		 * answerPath.setDirPath(".best-text");
		 */
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

		List<String> urlList = new ArrayList<String>();
		// http://www.ygdy8.net/html/gndy/dyzz/list_23_152.html
		for (int pageNo = 1; pageNo < 153; pageNo++) {
			urlList.add("http://www.ygdy8.net/html/gndy/dyzz/list_23_" + pageNo + ".html");
		}
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

		TemplateContentVO templateContentVO = new TemplateContentVO();
		templateContentVO.setHtmlPathList(cssPathList);
		task.setTemplateContentVO(templateContentVO);

		System.out.println("任务进度 : " + task.getSpeedProgress());

		TaskExecutionCenter taskExecutionCenter = new TaskExecutionCenter(task);
		taskExecutionCenter.run();
		System.out.println("任务进度 : " + task.getSpeedProgress());

	}
}
