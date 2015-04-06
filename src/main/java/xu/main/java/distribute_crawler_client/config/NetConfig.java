package xu.main.java.distribute_crawler_client.config;

public class NetConfig {

	public static final String INET_SOCKET_ADDRESS = "192.168.1.10";

	/* Task push nio server */

	public static final int NIO_TASK_QUERY_SERVER_PORT = 5667;

	public static final String NIO_CHARSET = "UTF-8";

	public static final int NIO_BYTE_BUFF_SIZE = 102400000;

	/* Task Speed Feedback Udp Server */

	public static final int UDP_TASK_SPEED_FEEDBACK_SERVER_PORT = 5668;

	// public static final int UDP_SPEED_DATA_LEN = 2048;

	/* Task Speed Feedback params */

	/* 进度反馈间隔,单位:% 例: 5 为 5% */
	public static final int UDP_SPEED_FEEDBACK_INTERVAL = 5;

	public static final int UDP_EXTRACT_RESULT_SERVER_PORT = 5669;

}
