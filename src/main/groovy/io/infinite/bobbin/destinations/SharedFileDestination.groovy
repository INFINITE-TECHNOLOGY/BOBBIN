package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.supplies.ast.cache.Cache

import java.util.concurrent.LinkedBlockingQueue

class SharedFileDestination extends Destination {

    class EventQueueRunnable extends Thread {

        Throwable throwable

        LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>()

        FileDestination fileDestination

        EventQueueRunnable(SharedFileDestination sharedFileDestination) {
            setFileDestination(new FileDestination(sharedFileDestination.getDestinationConfig(), sharedFileDestination.getParentBobbinConfig()))
            start()
        }

        @Override
        void run() {
            setName("Bobbin Async Logger")
            while (true) {
                while (!getEventQueue().isEmpty()) {
                    Event event = getEventQueue().peek()
                    getFileDestination().commonBinding(event)
                    getFileDestination().store(event)
                    getEventQueue().poll()
                }
                synchronized (getEventQueueRunnable()) {
                    if (!getEventQueue().isEmpty()) {
                        continue
                    }
                    getEventQueueRunnable().wait()
                }
            }
        }

    }

    @Cache
    EventQueueRunnable eventQueueRunnable = new EventQueueRunnable(this)

    EventQueueRunnable getEventQueueRunnable() {
        return eventQueueRunnable
    }

    @Override
    protected void store(Event event) {
        synchronized (getEventQueueRunnable()) {
            getEventQueueRunnable().getEventQueue().put(event)
            getEventQueueRunnable().notifyAll()
        }
    }

    SharedFileDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

}
