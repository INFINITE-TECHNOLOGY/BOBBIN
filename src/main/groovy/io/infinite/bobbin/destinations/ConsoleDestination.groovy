package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event

class ConsoleDestination extends Destination {

    @Override
    protected void store(Event event) {
        System.out.println(event.getFormattedMessage())
    }
}
