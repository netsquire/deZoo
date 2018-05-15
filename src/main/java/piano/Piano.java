package piano;

import examples.Melody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Configuration
public class Piano {

    private static final Logger LOG = Logger.getLogger(Piano.class.getName());

    @Autowired
    private Melody melody;

    private static Map<String, Map<EntityEvent.Operation, List<Task>>> taskFlow = new HashMap<>();
    private List<String> taskOrder = new LinkedList<>();
    private static AtomicInteger order = new AtomicInteger(0);
    private static AtomicInteger taskCounter = new AtomicInteger(0);
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public Future trigger(String typeName, String id, EntityEvent.Operation operation) {
        return executorService.submit(() -> {
            taskFlow.get(typeName).get(operation).forEach(this::executeTask);
            PianoLogger.logExecution(id);
            taskCounter.incrementAndGet();
        });
    }

    private void executeTasks() {
        taskOrder.forEach(typeName -> {
            taskFlow.get(typeName).get(EntityEvent.Operation.DEFAULT).forEach(this::executeTask);
        });
    }

    private void executeTask(Task task){
        if (task.getBefore() != null) {
            task.getBefore().executeJob();
        }
        if (task.getJob() != null) {
            task.getJob().executeJob();
            PianoLogger.reportEmptyTask(task.getId());
        }
        if (task.getAfter() != null) {
            task.getAfter().executeJob();
        }
    }

    private void trigger(EntityEvent event) {
        trigger(event.getTypeReference(), event.getId(), event.getOperation());
    }

    @Bean
    public static Piano piano() {
        return new Piano();
    }

    public void addJob(Job job) {
        putTask(new Task(job, nextTaskNumber(), UUID.randomUUID().toString()));
    }

    public Piano addJob(String id, Job job) {
        putTask(new Task(job, nextTaskNumber(), id));
        return this;
    }

    public void addJob(String id, Job job, int order) {
        putTask(new Task(job, order, id));
    }

    private void putTask(Task task) {
    }

    static int nextTaskNumber() {
        return order.incrementAndGet();
    }

    public void play(){
        melody.compose();
        displayPiano();
        executeTasks();
    }

    private void displayPiano() {
        taskFlow.keySet().forEach(typeName -> {
            for (EntityEvent.Operation operation : taskFlow.get(typeName).keySet()) {
                taskFlow.get(typeName).get(operation).forEach(this::dumpTask);
            }});

    }

    private void dumpTask(Task task){
        LOG.info("task ID: " + task.getId());
        LOG.info("task Number: " + task.getNumber());
        if (task.getBefore() != null) {
            LOG.info("before this task: " + task.getBefore().toString());
        }
        if (task.getAfter() != null) {
            LOG.info("after this task: " + task.getAfter().toString());
        }
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

    // TODO: List of tasks
    public List<Task> getTaskList(String typeName, EntityEvent.Operation operation){
        return taskFlow.get(typeName).get(operation);
    }

    public void setOrder(String... strings) {
        taskOrder = Arrays.asList(strings);
    }
}
