package io.infinite.bobbin.destinations


import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.supplies.ast.cache.Cache

class SharedConsoleDestination extends SharedDestination {

    @Cache
    Map<String, EventQueueRunnable> eventQueueRunnableMap = [:]

    SharedConsoleDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

    @Override
    Destination getActualDestination() {
        return new ConsoleDestination(destinationConfig, parentBobbinConfig)
    }

    @Override
    String getSharedDestinationName() {
        return "Bobbin Async Console Logger"
    }
}
