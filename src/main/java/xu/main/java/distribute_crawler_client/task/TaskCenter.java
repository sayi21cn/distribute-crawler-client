package xu.main.java.distribute_crawler_client.task;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import xu.main.java.distribute_crawler_common.nio_data.TaskVO;

public class TaskCenter {

	/* 未开始任务队列 */
	private static Queue<TaskVO> waitTaskQueue = new LinkedBlockingDeque<TaskVO>();

	/* 任务进度反馈队列 */
	private static Queue<TaskVO> taskSpeedFeedbackQueue = new LinkedBlockingDeque<TaskVO>();

	public static boolean offerTaskToWaitQueue(TaskVO taskVO) {
		return waitTaskQueue.offer(taskVO);
	}

	public static TaskVO pollTaskFromWaitQueue() {
		return waitTaskQueue.poll();
	}

	public static boolean offerTaskFeedback(TaskVO taskVO) {
		return taskSpeedFeedbackQueue.offer(taskVO);
	}

	public static TaskVO pollTaskFeedback() {
		return taskSpeedFeedbackQueue.poll();
	}

}
// Queue
// offer 添加一个元素并返回true 如果队列已满，则返回false
// poll 移除并返问队列头部的元素 如果队列为空，则返回null
// peek 返回队列头部的元素 如果队列为空，则返回null
// put 添加一个元素 如果队列满，则阻塞
// take 移除并返回队列头部的元素 如果队列为空，则阻塞
