package xu.main.java.distribute_crawler_client.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import xu.main.java.distribute_crawler_client.config.NioClientConfig;
import xu.main.java.distribute_crawler_client.task.TaskCenter;
import xu.main.java.distribute_crawler_common.nio_data.TaskVO;
import xu.main.java.distribute_crawler_common.util.GsonUtil;

public class TaskQueryNioClient extends Thread {

	private Selector selector;

	private Charset charset = Charset.forName(NioClientConfig.NIO_CHARSET);

	private SocketChannel sc = null;

	@Override
	public void run() {

		try {

			init();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void init() throws IOException {

		selector = Selector.open();

		InetSocketAddress inetSocketAddress = new InetSocketAddress(NioClientConfig.INET_SOCKET_ADDRESS, NioClientConfig.TASK_QUERY_NIO_SERVER_PORT);

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

						ByteBuffer buff = ByteBuffer.allocate(1024);

						String content = "";

						while (sc.read(buff) > 0) {

							buff.flip();

							content += charset.decode(buff);
						}

						System.out.println("client接收信息: " + content);

						try {

							TaskVO taskVO = GsonUtil.fromJson(content, TaskVO.class);

							TaskCenter.offerTaskToWaitQueue(taskVO);

						} catch (Exception e) {

							e.printStackTrace();
						}

						sk.interestOps(SelectionKey.OP_READ);
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// new ClientReaderThread(selector).start();

		// while (true) {
		//
		// sc.write(charset.encode(QueryJsonData.TASK_QUERY_JSON));
		//
		// try {
		//
		// Thread.sleep(NioClientConfig.QUERY_TASK_INTERVAL);
		//
		// } catch (InterruptedException e) {
		//
		// e.printStackTrace();
		// }
		// }
	}
}
