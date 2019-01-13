package io.infinite.bobbin.tests_2_x_x

import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.bobbin.destinations.SharedFileDestination

class TestSharedFileDestination extends SharedFileDestination {

    static TestSharedFileDestination instance

    TestSharedFileDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
        instance = this
    }
}
