package hammer;

import tasks.NailTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class SteamHammer {

	private static Map<Class, NailTask> jobTasks = new HashMap<>();
	private static BlockingQueue<Traceable> events = new PriorityBlockingQueue<>();
	private Consumer consumer = new Consumer(events);

	void launch(){
		new Thread(consumer).start();
	}

	NailTask putTask(NailTask task, String id) {
		return jobTasks.put(task.getType(), task);
	}

	NailTask removeTask(String taskId) {
		return jobTasks.remove(taskId);
	}

	private Map<Class, NailTask> getJobTasks() {
		return jobTasks;
	}

	class Consumer implements Runnable {

		private final BlockingQueue queue;

		Consumer(BlockingQueue q) {
			queue = q;
		}

		public void run() {
			try {
				while (true) {
					consume((Traceable) queue.take());
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

		void consume(Traceable task) {
			// calculate priority ??
			getJobTasks().get(task.getType()).process(task.getId());
			pickTrigger();
		}

		private void pickTrigger() {
		}
	}
}