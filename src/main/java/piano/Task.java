package piano;

public class Task {

    private Job job;
    private int number;
    private String id;

    private Job before;
    private Job after;

    Task(Job job, int number, String id) {
        this.job = job;
        this.number = number;
        this.id = id;
    }

    public Task(Job job, int number, String id, Job before, Job after) {
        this.job = job;
        this.number = number;
        this.id = id;
        this.before = before;
        this.after = after;
    }

    Task(Job job) {
        this(job, Piano.nextTaskNumber(), java.util.UUID.randomUUID().toString());
    }

    Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    Job getBefore() {
        return before;
    }

    Job getAfter() {
        return after;
    }

    public void setAfter(Job job) {
        after = job;
    }

    public void setBefore(Job job){
            before = job;
    }

}
