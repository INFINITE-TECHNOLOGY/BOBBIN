package io.infinite.bobbin.destinations

import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig

class ConsoleDestination extends Destination {

    ConsoleDestination(DestinationConfig destinationConfig) {
        super(destinationConfig)
    }

    @Override
    protected void store(String finalOutputMessageText, Level level, String className, String date) {
        System.out.print(finalOutputMessageText)
    }
}
