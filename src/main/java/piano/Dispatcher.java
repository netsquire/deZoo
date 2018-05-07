package piano;

import jdk.internal.org.objectweb.asm.TypeReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

public class Dispatcher {

    private Map<Class<?>, BlockingDeque<Job>> eventQueues = new HashMap<>();

    public void addResponsableforType(TypeReference type, Job job){
        eventQueues.get(type).add(job);
    }


}
