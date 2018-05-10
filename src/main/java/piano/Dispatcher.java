package piano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class Dispatcher {

    @Autowired
    private Map<String, LinkedBlockingQueue<EntityEvent>> eventQueues;
    private Map<String, Thread> threads = new HashMap<>();

    //ExecutorService threadPool = Executors.newFixedThreadPool(5);

    @Bean
    Map<String, LinkedBlockingQueue<EntityEvent>> eventQueues(){
        return new HashMap<>();
    }

    @Bean
    LinkedBlockingQueue<EntityEvent> linkedBlockingQueue(){
        return new LinkedBlockingQueue<>();
    }

    public void addResponsableforType(String typeReference, EntityEvent event) {
        eventQueues.get(typeReference).add(event);
    }

    public void notify(String typeName, String id, EntityEvent.Operation operation) {
        getOrCreate(eventQueues.get(typeName))
                .add(new EntityEvent(typeName, id, operation));
    }

    public void notify(EntityEvent event) {
        notify(event.getTypeReference(), event.getId(), event.getOperation());
    }

    private LinkedBlockingQueue<EntityEvent> getOrCreate(LinkedBlockingQueue<EntityEvent> queue){
        if (queue == null) {
            queue = linkedBlockingQueue();
        }
        return queue;
    }

    public Map<String, LinkedBlockingQueue<EntityEvent>> getEventQueues() {
        return eventQueues;
    }

    public void play() {
        for (String type : eventQueues.keySet()) {
            Thread t = new Thread(new EventConsumer(eventQueues.get(type)));
            threads.put(type, t);
            t.start();
        }
    }

    class EventConsumer implements Runnable {
        LinkedBlockingQueue<EntityEvent> eventQueue;
        EventConsumer(LinkedBlockingQueue<EntityEvent> eventQueue) {
            this.eventQueue = eventQueue;
        }

        @Override
        public void run() {
            EntityEvent event;
            try {
                while ((event = eventQueue.take()).getOperation() != null) {
                    System.out.println("Processing for event: " + event.getId() + " of type=" + event.getTypeReference());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}