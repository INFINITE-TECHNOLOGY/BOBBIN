package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event
import io.infinite.supplies.ast.cache.Static

import javax.script.ScriptEngine
import java.util.concurrent.LinkedBlockingQueue

class SharedFileDestination extends Destination {

    class EventQueueRunnable extends Thread {

        Throwable throwable

        LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>()

        FileDestination fileDestination

        EventQueueRunnable(SharedFileDestination sharedFileDestination) {
            fileDestination = new FileDestination(sharedFileDestination.getDestinationConfig(), sharedFileDestination.getParentBobbinConfig(), sharedFileDestination.getScriptEngine())
            start()
        }

        @Override
        void run() {
            setName("Bobbin Async Logger")
            while (true) {
                synchronized (getEventQueueRunnable()) {
                    while (!eventQueue.isEmpty()) {
                        fileDestination.store(eventQueue.poll())
                    }
                    getEventQueueRunnable().wait()
                }
            }
        }

    }

    @Static
    final EventQueueRunnable eventQueueRunnable = new EventQueueRunnable(this)

    @Override
    protected void store(Event event) {
        synchronized (getEventQueueRunnable()) {
            getEventQueueRunnable().getEventQueue().put(event)
            getEventQueueRunnable().notifyAll()
        }
    }

    SharedFileDestination(BobbinConfig.Destination destinationConfig, BobbinConfig parentBobbinConfig, ScriptEngine scriptEngine) {
        super(destinationConfig, parentBobbinConfig, scriptEngine)
    }

}
