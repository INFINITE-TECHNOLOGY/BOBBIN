package io.infinite.bobbin.destinations

import io.infinite.bobbin.Level
import io.infinite.bobbin.config.ConsoleDestinationConfig

class ConsoleDestination extends Destination {

    ConsoleDestination(ConsoleDestinationConfig destinationConfig) {
        super(destinationConfig)
    }

    @Override
    protected void store(String finalOutputMessageText, String className, Level level, String date) {
        if (level != Level.ERROR) {
            System.out.print(finalOutputMessageText)
        } else {
            System.err.print(finalOutputMessageText)
        }
    }
}
