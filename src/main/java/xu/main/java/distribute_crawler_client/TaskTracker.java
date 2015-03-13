package xu.main.java.distribute_crawler_client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import xu.main.java.distribute_crawler_client.extractor.CssExtractor;
import xu.main.java.distribute_crawler_client.extractor.IExtractor;
import xu.main.java.distribute_crawler_client.util.GsonUtil;
import xu.main.java.distribute_crawler_client.util.HttpDownload;
import xu.main.java.distribute_crawler_client.util.MysqlUtil;
import xu.main.java.distribute_crawler_client.util.StringHandler;
import xu.main.java.distribute_crawler_common.vo.HtmlPath;

public class TaskTracker extends Thread {

	private List<String> urlList;
	private List<HtmlPath> cssPathList;
	private String charset;

	public TaskTracker(List<String> urlList, List<HtmlPath> cssPathList, String charset) {
		this.urlList = urlList;
		this.cssPathList = cssPathList;
		this.charset = charset;
	}

	@Override
	public void run() {
		Connection conn = null;
		try {
			conn = MysqlUtil.getConnection();
			System.out.println("db connection done .");
			Thread.sleep(1000);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String url : urlList) {
			System.out.println("download: " + url);
			String html = HttpDownload.download(url, this.charset);
			IExtractor extractor = new CssExtractor();
			Map<String, String> resultMap = extractor.extractorColumns(html, cssPathList);
			String sql = buildSaveSQL(resultMap);
			boolean result = MysqlUtil.saveToDb(conn, sql);
			System.out.print("数据保存数据库 ");
			System.out.println(result ? "成功" : "失败");
		}

	}

	private String buildSaveSQL(Map<String, String> resultMap) {
		StringBuffer sqlBuffer = new StringBuffer("insert into dytt_movie_catalog (");
		for (HtmlPath cssPath : cssPathList) {
			sqlBuffer.append(cssPath.getPathName()).append(",");
		}
		deleteBufferLast(sqlBuffer, 1);
		sqlBuffer.append(") values ('");
		for (HtmlPath cssPath : cssPathList) {
			sqlBuffer.append(StringHandler.nullToEmpty(resultMap.get(cssPath.getPathName()))).append("','");
		}
		deleteBufferLast(sqlBuffer, 2);
		sqlBuffer.append(");");
		System.out.println(sqlBuffer);
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
		
		TaskTracker taskTracker = new TaskTracker(urlList, cssPathList, "gb2312");
		taskTracker.run();

	}
}
