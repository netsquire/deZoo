package piano;

import examples.SpecificEntity;
import jdk.internal.org.objectweb.asm.TypeReference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * TODO:
 * 1) concurrency
 * 2) events -> store routines
 *
 */
public class Piano {

    private static Map<String, Task> taskFlow = new HashMap<>();
    private List<String> taskOrder = new LinkedList<>();
    private static AtomicInteger order = new AtomicInteger(0);

    public static Piano build() {
        return new Piano();
    }

    public void addJob(Job job) {
        putTask(new Task(job, nextTaskNumber(), UUID.randomUUID().toString()));
    }

    public void addJob(String id, Job job) {
        putTask(new Task(job, nextTaskNumber(), id));
    }

    public void addJob(String id, Job job, int order) {
        putTask(new Task(job, order, id));
    }

    private void putTask(Task task) {
        String id = task.getId();
	    taskFlow.put(id, task);
	    taskOrder.add(id);
    }

    static int nextTaskNumber() {
        return order.incrementAndGet();
    }

    public void play(){
        process();
    }

    /**
     *
     * TODO:
     * 1) concurrency - shift to event firing & processing OR akka ?
     * 2) events -> store routines
     *
     */
    private void process() {
        taskOrder.forEach(t -> {
            Task task = taskFlow.get(t);
            System.out.print(t);
            System.out.println(" : " + task.getNumber());

            // fire (submit) in separated thread

            // take-execute pre-jobs
            if (task.getBefore() != null) {
                Job jobBefore = task.getBefore();
                System.out.println("Executes before = " + jobBefore);
                jobBefore.executeJob();
            }

            System.out.println(taskFlow.get(t).getJob().executeJob());

            // take-execute post-jobs
            if (task.getAfter() != null) {
                System.out.println("Executes after = " + task.getAfter());
                task.getAfter().executeJob();
            }
        });
    }

    static boolean taskPresent(String t) {
        return taskFlow.keySet().contains(t);
    }

    public Task defineTask(Job job) {
        return new Task(job);
    }

    public void addTask(Task task) {
        putTask(task);
    }


    public <T> T  getEntity(TypeReference typeReference, String id) {
        return null;
    }

    public void persistEntity(SpecificEntity entity) {

    }
}
