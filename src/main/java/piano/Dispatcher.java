package piano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class Dispatcher {

    @Autowired
    private Map<String, LinkedBlockingQueue<Event>> eventQueues; // = new HashMap<>();
    private Map<String, Thread> threads = new HashMap<>();

    @Bean
    Map<String, LinkedBlockingQueue<Event>> eventQueues(){
        return new HashMap<>();
    }

    @Bean
    private LinkedBlockingQueue<Event> linkedBlockingQueue(){
        return new LinkedBlockingQueue<>();
    }

    public void addResponsableforType(String typeReference, Event event) {
        eventQueues.get(typeReference).add(event);
    }

    public void notify(String typeName, String id, Event.Operation operation) {
        getOrCreate(eventQueues.get(typeName))
                .add(new Event(typeName, id, operation));
    }

    private LinkedBlockingQueue<Event> getOrCreate(LinkedBlockingQueue<Event> queue){
        if (queue == null) {
            queue = linkedBlockingQueue();
        }
        return queue;
    }

    public Map<String, LinkedBlockingQueue<Event>> getEventQueues() {
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

        LinkedBlockingQueue<Event> eventQueue;

        EventConsumer(LinkedBlockingQueue<Event> eventQueue) {
            this.eventQueue = eventQueue;
        }

        @Override
        public void run() {
            Event event;
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