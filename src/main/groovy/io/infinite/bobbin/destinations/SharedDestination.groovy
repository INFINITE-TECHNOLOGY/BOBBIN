package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig

abstract class SharedDestination extends Destination {

    String getSharingKey() {
        return destinationConfig.properties.get("sharingKey") ?: "default"
    }

    EventQueueRunnable getEventQueueRunnable() {
        EventQueueRunnable eventQueueRunnable = eventQueueRunnableMap.get(sharingKey)
        if (eventQueueRunnable != null) {
            return eventQueueRunnable
        } else {
            eventQueueRunnable = new EventQueueRunnable(getActualDestination(), getSharedDestinationName())
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

    SharedDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

    abstract Destination getActualDestination()

    abstract String getSharedDestinationName()

    abstract Map<String, EventQueueRunnable> getEventQueueRunnableMap()

}
