package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event
import io.infinite.supplies.ast.cache.Static

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
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

    @Static
    final EventQueueRunnable eventQueueRunnable = new EventQueueRunnable(this)

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

    SharedFileDestination(BobbinConfig.Destination destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

}
