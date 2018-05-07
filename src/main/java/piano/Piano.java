package piano;

import entities.SpecificEntity;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Configuration
public class Piano {

    private static final Logger LOG = Logger.getLogger(Piano.class.getName());

    private static Map<String, Task> taskFlow = new HashMap<>();
    private List<String> taskOrder = new LinkedList<>();
    private static AtomicInteger order = new AtomicInteger(0);

    @Bean
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
        displayPiano();
        executeTasks();
    }

    private void executeTasks() {
        taskOrder.forEach(t -> {
            Task task = taskFlow.get(t);
//            LOG.info(t);
//            LOG.info(" : " + task.getNumber());

            // take-execute pre-jobs
            if (task.getBefore() != null) {
                Job jobBefore = task.getBefore();
                //LOG.info("Executes before = " + jobBefore);
                jobBefore.executeJob();
            }
            taskFlow.get(t).getJob().executeJob();

            // take-execute post-jobs
            if (task.getAfter() != null) {
                //LOG.info("Executes after = " + task.getAfter());
                task.getAfter().executeJob();
            }
        });
    }

    private void displayPiano() {
        taskFlow.keySet().forEach(k -> {
            Task task = taskFlow.get(k);
            LOG.info("task ID: " + task.getId());
            LOG.info("task Number: " + task.getNumber());
            if (task.getBefore() != null) { LOG.info("before this task: " + task.getBefore().toString());}
            if (task.getAfter() != null ) {LOG.info("after this task: " + task.getAfter().toString());}
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
