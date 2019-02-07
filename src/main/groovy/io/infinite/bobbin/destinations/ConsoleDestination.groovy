package io.infinite.bobbin.destinations

import groovy.transform.CompileStatic
import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig

@CompileStatic
class ConsoleDestination extends Destination {

    ConsoleDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

    @Override
    protected void store(Event event) {
        System.out.print(event.getFormattedMessage())
    }
}
