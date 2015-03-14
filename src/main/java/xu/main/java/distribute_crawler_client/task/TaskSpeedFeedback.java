package xu.main.java.distribute_crawler_client.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import xu.main.java.distribute_crawler_client.JobTracker;
import xu.main.java.distribute_crawler_client.config.TaskTrackerConfig;

/**
 * 
 * 任务进度反馈
 * 
 * @author xu
 * 
 */
public class TaskSpeedFeedback extends Thread {

	private Logger logger = Logger.getLogger(TaskSpeedFeedback.class);

	public static final Map<Integer, Task> TASK_MAP = new HashMap<Integer, Task>();

	private JobTracker jobTracker = new JobTracker();

	@Override
	public void run() {
		logger.info("TaskSpeedFeedback: start !");
		while (true) {

			Map<Integer, Integer> speedMap = queryTaskSpeed();

			jobTracker.taskSpeedFeedback(speedMap);

			try {
				Thread.sleep(TaskTrackerConfig.TASK_RESULT_FEEDBACK_INTERVAL);
			} catch (InterruptedException e) {
				logger.error("TaskResultFeedback: sleep error !", e);
			}

		}
	}

	public Map<Integer, Integer> queryTaskSpeed() {
		synchronized (TASK_MAP) {
			Map<Integer, Integer> speedMap = new HashMap<Integer, Integer>();
			for (Iterator<Entry<Integer, Task>> it = TASK_MAP.entrySet().iterator(); it.hasNext();) {
				Entry<Integer, Task> entry = it.next();
				Task task = entry.getValue();
				speedMap.put(entry.getKey(), task.getSpeedProgress());
				if (task.getSpeedProgress() == 100) {
					TASK_MAP.remove(task.getTaskId());
				}
			}
			return speedMap;
		}
	}

}
