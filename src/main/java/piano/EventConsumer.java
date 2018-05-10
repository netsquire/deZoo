package piano;

import java.util.concurrent.LinkedBlockingQueue;

public class EventConsumer implements Runnable {
    private LinkedBlockingQueue<EntityEvent> eventQueue;

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
