package piano;

public class PianoLogger {

    /**
     * Explicitly - logging
     * and implicitly - verification the presence of incomplete tasks, jobs and events
     *
     * @param id
     */
    public static void logExecution(String id) {
        // log execution
        // FILE, DB, MEMORY, etc...

        // in no more tasks to execute - shutdown executeService
    }

    public static void reportEmptyTask(String id) {
    }
}
