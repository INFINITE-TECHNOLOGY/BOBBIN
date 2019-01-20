package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.supplies.ast.cache.Cache

import java.util.concurrent.LinkedBlockingQueue

class SharedFileDestination extends Destination {

    class EventQueueRunnable extends Thread {

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
    Map<String, EventQueueRunnable> eventQueueRunnableMap = [:]

    String getSharingKey() {
        return destinationConfig.properties.get("sharingKey") ?: "default"
    }

    EventQueueRunnable getEventQueueRunnable() {
        EventQueueRunnable eventQueueRunnable = eventQueueRunnableMap.get(sharingKey)
        if (eventQueueRunnable != null) {
            return eventQueueRunnable
        } else {
            eventQueueRunnable = new EventQueueRunnable(this)
            setEventQueueRunnable(eventQueueRunnable)
            return eventQueueRunnable
        }
    }

    void setEventQueueRunnable(EventQueueRunnable eventQueueRunnable) {
        eventQueueRunnableMap.put(sharingKey, eventQueueRunnable)
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
