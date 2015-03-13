package xu.main.java.distribute_crawler_client.config;

public class DbConfig {

	public static final String SPLIT_STRING = "###";

	public static final String SERVER_IP = "127.0.0.1";

	public static final int SERVER_PORT = 3306;

	public static final String DB_NAME = "movie_crawler";

	public static final String DB_USER_NAME = "root";

	public static final String DB_PASS_WORD = "roots";

	public static final String DB_URL = "jdbc:mysql://" + SERVER_IP + ":" + SERVER_PORT + "/" + DB_NAME + "?useUnicode=true&characterEncoding=utf-8";

}
