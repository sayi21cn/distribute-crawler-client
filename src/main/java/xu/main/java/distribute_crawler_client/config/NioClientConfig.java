package xu.main.java.distribute_crawler_client.config;

public class NioClientConfig {

	public static final int TASK_QUERY_NIO_SERVER_PORT = 5667;

	public static final int TASK_RECORD_NIO_SERVER_PORT = 5668;

	public static final String NIO_CHARSET = "UTF-8";

	public static final String INET_SOCKET_ADDRESS = "192.168.1.10";

	public static final int QUERY_TASK_INTERVAL = 1000 * 60;
	
	public static final int BYTE_BUFF_SIZE = 10240000;

}
