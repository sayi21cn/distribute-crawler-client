package xu.main.java.distribute_crawler_client;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import xu.main.java.distribute_crawler_common.vo.TaskRecord;
/**
 * 任务收集
 *@author xu
 *
 */
public class JobCenter {
	/* 任务进度收集 <taskRecordId,taskRecord> */
	private static Map<Integer, TaskRecord> taskRecordMap = new HashMap<Integer, TaskRecord>();

	/* 已完成任务收集 */
	private static Queue<TaskRecord> doneTaskRecordQueue = new LinkedBlockingDeque<TaskRecord>();

	public boolean addDoneTaskRecord(TaskRecord taskRecord) {
		return doneTaskRecordQueue.offer(taskRecord);
	}

	public TaskRecord pollDoneTaskRecord() {
		return doneTaskRecordQueue.poll();
	}

	/**
	 * 更新任务进度
	 * 
	 * @param taskRecordId
	 * @param speed
	 * @return
	 */
	public boolean updateSpeed(int taskRecordId, String speed) {
		TaskRecord taskRecord = taskRecordMap.get(taskRecordId);
		if (null == taskRecord) {
			return false;
		}
		taskRecord.setTask_status(speed);
		return true;
	}

	/**
	 * 将TaskRecord从taskRecordMap中移除
	 * 
	 * @param taskRecord
	 * @return
	 */
	public boolean deleteTaskRecordFromMap(TaskRecord taskRecord) {
		if (null == taskRecordMap.get(taskRecord.getId())) {
			return false;
		}
		taskRecordMap.remove(taskRecord.getId());
		return true;
	}

	public boolean addTaskRecord(TaskRecord taskRecord) {
		if (null != taskRecordMap.get(taskRecord.getId())) {
			return false;
		}
		taskRecordMap.put(taskRecord.getId(), taskRecord);
		return true;

	}

	public static void main(String[] args) {
		TaskRecord taskRecord = new TaskRecord();
		taskRecord.setId(1);
		taskRecord.setTask_status("20");

		System.out.println(taskRecord.getTask_status());

		JobCenter jobCenter = new JobCenter();
		jobCenter.addTaskRecord(taskRecord);
		jobCenter.updateSpeed(1, "30");

		System.out.println(taskRecord.getTask_status());

	}
}
