package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event

import java.util.concurrent.LinkedBlockingQueue

class EventQueueRunnable extends Thread {

    LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>()

    Destination sharedDestination

    String eventQueueRunnableName

    EventQueueRunnable(Destination sharedDestination, String eventQueueRunnableName) {
        setSharedDestination(sharedDestination)
        this.eventQueueRunnableName = eventQueueRunnableName
        start()
    }

    @Override
    void run() {
        setName(eventQueueRunnableName)
        while (true) {
            while (!eventQueue.isEmpty()) {
                Event event = getEventQueue().peek()
                sharedDestination.commonBinding1(event)
                sharedDestination.commonBinding2(event)
                sharedDestination.store(event)
                eventQueue.poll()
            }
            synchronized (this) {
                if (!eventQueue.isEmpty()) {
                    continue
                }
                this.wait()
            }
        }
    }

}