package xu.main.java.distribute_crawler_client.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Queue;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.config.NetConfig;
import xu.main.java.distribute_crawler_common.util.StringHandler;

public class TaskQueryNioClient extends Thread {

	private Selector selector;

	private Charset charset = Charset.forName(NetConfig.NIO_CHARSET);

	private Logger logger = Logger.getLogger(TaskQueryNioClient.class);

	private SocketChannel sc = null;

	private Queue<String> queue = null;

	@Override
	public void run() {
		
		if (null == this.queue) {
			logger.error("TaskQueryNioClient queue Null, return");
			return;
		}

		try {
			startListen();
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void startListen() throws IOException {

		selector = Selector.open();

		InetSocketAddress inetSocketAddress = new InetSocketAddress(NetConfig.INET_SOCKET_ADDRESS, NetConfig.NIO_TASK_QUERY_SERVER_PORT);

		logger.info(String.format("Nio client open server : [ %s ] Port : [ %s ]", NetConfig.INET_SOCKET_ADDRESS, NetConfig.NIO_TASK_QUERY_SERVER_PORT));

		sc = SocketChannel.open(inetSocketAddress);

		sc.configureBlocking(false);

		sc.register(selector, SelectionKey.OP_READ);
		// 只接收Server端推送的任务数据
		try {
			while (selector.select() > 0) {

				for (SelectionKey sk : selector.selectedKeys()) {

					selector.selectedKeys().remove(sk);

					if (sk.isReadable()) {

						SocketChannel sc = (SocketChannel) sk.channel();

						ByteBuffer buff = ByteBuffer.allocate(NetConfig.NIO_BYTE_BUFF_SIZE);

						String content = "";

						while (sc.read(buff) > 0) {

							buff.flip();

							content += charset.decode(buff);
						}

						logger.info("client接收信息: " + content);

						// TaskVO taskVO = GsonUtil.fromJson(content,
						// TaskVO.class);

						if (!StringHandler.isNullOrEmpty(content)) {
							this.queue.offer(content);
							logger.info(String.format("offer a task, size : [%s], queue hash : [%s]", this.queue.size(), this.queue.hashCode()));
						} else {
							logger.error("服务端推送任务为空,系统出现异常！退出！");
							System.exit(1);
						}
						sk.interestOps(SelectionKey.OP_READ);
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}
}
