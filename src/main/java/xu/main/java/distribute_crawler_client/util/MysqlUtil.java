package xu.main.java.distribute_crawler_client.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import xu.main.java.distribute_crawler_client.config.DbConfig;

public class MysqlUtil {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {

		String mysqlUrl = "jdbc:mysql://" + DbConfig.SERVER_IP + ":" + DbConfig.SERVER_PORT + "/" + DbConfig.DB_NAME + "?useUnicode=true&characterEncoding=utf-8";
		return DriverManager.getConnection(mysqlUrl, DbConfig.DB_USER_NAME, DbConfig.DB_PASS_WORD);
	}

	public static boolean saveToDb(Connection conn, String sql) {
		Statement stat = null;
		boolean result = true;
		try {
			stat = conn.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
