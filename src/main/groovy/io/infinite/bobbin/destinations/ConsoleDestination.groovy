package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event
import io.infinite.speedometer.Speedometer

@Speedometer
class ConsoleDestination extends Destination {

    @Override
    protected void store(Event event) {
        System.out.print(event.getFormattedMessage())
    }
}
