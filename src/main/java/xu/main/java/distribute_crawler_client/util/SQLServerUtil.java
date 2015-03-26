package xu.main.java.distribute_crawler_client.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import xu.main.java.distribute_crawler_client.config.ServerDbConfig;

public class SQLServerUtil {
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(String ip,String port) throws SQLException, ClassNotFoundException {

		String mysqlUrl = "jdbc:mysql://localhost:3306/" + ServerDbConfig.DB_NAME + "?useUnicode=true&characterEncoding=utf-8";
		return DriverManager.getConnection(mysqlUrl, ServerDbConfig.DB_USER_NAME, ServerDbConfig.DB_PASS_WORD);
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
