package io.infinite.bobbin.config

import io.infinite.bobbin.destinations.FileDestination

class FileDestinationConfig extends AbstractDestinationConfig {

    String fileName

    @Override
    Class getDestinationClass() {
        return FileDestination.class
    }

}
