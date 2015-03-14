package xu.main.java.distribute_crawler_client.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.util.MysqlUtil;
import xu.main.java.distribute_crawler_common.vo.TaskRecord;

/**
 * 数据库操作类
 * 
 * @author xu
 * 
 */
public class DbDao {

	private Logger logger = Logger.getLogger(DbDao.class);

	private final String QUERY_TEMPLATE_BY_ID_SQL = "select template_area from template where id = ?;";

	private final String QUERY_ALL_WAIT_TASKRECORDS = "select id,task_name,template_id,insert_db_table_name,is_use_db_url,data_category,urls_or_sql,task_describtion,task_create_date,task_update_time,download_thread_num,task_status from task where task_status = '0' order by id asc";

	public List<TaskRecord> queryAllWaitTaskRecords() {
		List<TaskRecord> taskRecordList = new ArrayList<TaskRecord>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			Connection conn = MysqlUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(QUERY_ALL_WAIT_TASKRECORDS);
			while (rs.next()) {
				TaskRecord taskRecord = new TaskRecord();
				taskRecord.setId(rs.getInt("id"));
				taskRecord.setTask_name(rs.getString("task_name"));
				taskRecord.setTemplate_id(rs.getString("template_id"));
				taskRecord.setInsert_db_table_name(rs.getString("insert_db_table_name"));
				taskRecord.setIs_use_db_url(rs.getString("is_use_db_url"));
				taskRecord.setData_category(rs.getString("data_category"));
				taskRecord.setUrls_or_sql(rs.getString("urls_or_sql"));
				taskRecord.setTask_describtion(rs.getString("task_describtion"));
				taskRecord.setTask_create_date(rs.getString("task_create_date"));
				taskRecord.setTask_update_time(rs.getString("task_update_time"));
				taskRecord.setDownload_thread_num(rs.getString("download_thread_num"));
				taskRecord.setTask_status(rs.getString("task_status"));
				taskRecordList.add(taskRecord);
			}
		} catch (ClassNotFoundException e) {
			logger.error("Class not found Exception", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} finally {
			MysqlUtil.closeResultSet(rs);
			MysqlUtil.closeStatement(stmt);
		}

		return taskRecordList;
	}

	public String queryTemplateById(String templateId) {
		ResultSet rs = null;
		String templateArea = "{}";
		try {
			Connection conn = MysqlUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(QUERY_TEMPLATE_BY_ID_SQL);
			pstmt.setString(1, templateId);
			rs = pstmt.executeQuery();
			rs.next();
			templateArea = rs.getString("template_area");
		} catch (ClassNotFoundException e) {
			logger.error("Class not found Exception", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}
		return templateArea;
	}

	public List<String> queryUrlBySql(String sql) {
		ResultSet rs = null;
		List<String> urlList = new ArrayList<String>();
		try {
			Connection conn = MysqlUtil.getConnection();
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				urlList.add(rs.getString("url"));
			}
		} catch (ClassNotFoundException e) {
			logger.error("Class not found Exception", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}
		return urlList;
	}

}
