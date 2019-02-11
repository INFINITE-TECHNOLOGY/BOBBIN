package io.infinite.bobbin.tests_2_x_x


import io.infinite.bobbin.config.DestinationConfig
import io.infinite.bobbin.destinations.FileDestination

class TestSharedFileDestination extends FileDestination {

    static TestSharedFileDestination instance

    TestSharedFileDestination(DestinationConfig destinationConfig) {
        super(destinationConfig)
        instance = this
    }

}
