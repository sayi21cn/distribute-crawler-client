package xu.main.java.distribute_crawler_client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.util.MysqlUtil;

/**
 * 数据库操作类
 * 
 * @author xu
 * 
 */
public class DbTracker {

	private Logger logger = Logger.getLogger(DbTracker.class);

	private final String QUERY_TEMPLATE_BY_ID_SQL = "select template_area from template where id = ?;";

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
