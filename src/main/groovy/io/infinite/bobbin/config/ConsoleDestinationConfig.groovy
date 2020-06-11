package io.infinite.bobbin.config


import io.infinite.bobbin.destinations.ConsoleDestination

class ConsoleDestinationConfig extends AbstractDestinationConfig {

    @Override
    Class getDestinationClass() {
        return ConsoleDestination.class
    }

}
