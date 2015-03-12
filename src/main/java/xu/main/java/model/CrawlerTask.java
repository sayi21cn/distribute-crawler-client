package xu.main.java.model;

import java.util.Date;
import java.util.List;

public class CrawlerTask {

	/* 任务名称 */
	private String taskName;

	/* 创建时间 */
	private Date createTime;

	/* 完成时间 */
	private Date doneTiem;

	/* 任务类型，增量与全量 */
	private int taskType;

	/* 数据库表名称 */
	private String dbTableName;

	/* 数据库字段名称列表 */
	private List<String> dbColumnList;

	/* 是否使用cookie */
	private boolean isUseCookie;

	/* Cookie更新间隔,毫秒 */
	private long cookieUpdateTime;

	/* 抽取URL列表 */
	private List<String> urlList;

	/* 抽取内容列表 */
	private List<HtmlPath> cssPathList;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDoneTiem() {
		return doneTiem;
	}

	public void setDoneTiem(Date doneTiem) {
		this.doneTiem = doneTiem;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getDbTableName() {
		return dbTableName;
	}

	public void setDbTableName(String dbTableName) {
		this.dbTableName = dbTableName;
	}

	public List<String> getDbColumnList() {
		return dbColumnList;
	}

	public void setDbColumnList(List<String> dbColumnList) {
		this.dbColumnList = dbColumnList;
	}

	public boolean isUseCookie() {
		return isUseCookie;
	}

	public void setUseCookie(boolean isUseCookie) {
		this.isUseCookie = isUseCookie;
	}

	public long getCookieUpdateTime() {
		return cookieUpdateTime;
	}

	public void setCookieUpdateTime(long cookieUpdateTime) {
		this.cookieUpdateTime = cookieUpdateTime;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public List<HtmlPath> getCssPathList() {
		return cssPathList;
	}

	public void setCssPathList(List<HtmlPath> cssPathList) {
		this.cssPathList = cssPathList;
	}

}
