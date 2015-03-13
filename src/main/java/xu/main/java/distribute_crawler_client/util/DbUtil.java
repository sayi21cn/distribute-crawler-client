package xu.main.java.distribute_crawler_client.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.DbConfig;

public class DbUtil {

	private static Logger logger = Logger.getLogger(DbUtil.class);
	private static Connection conn = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		}
	}

	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(DbConfig.DB_URL, DbConfig.DB_USER_NAME, DbConfig.DB_PASS_WORD);
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
		return conn;
	}

	public static void resultSetClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
	}

	public static void preparedStatementClose(PreparedStatement pstat) {
		if (pstat != null) {
			try {
				pstat.close();
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
	}

	public static void statementClose(Statement stat) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
	}

}
