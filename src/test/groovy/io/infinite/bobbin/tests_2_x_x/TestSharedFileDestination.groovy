package io.infinite.bobbin.tests_2_x_x

import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.destinations.SharedFileDestination

import javax.script.ScriptEngine

class TestSharedFileDestination extends SharedFileDestination{

    static TestSharedFileDestination instance

    TestSharedFileDestination(BobbinConfig.Destination destinationConfig, BobbinConfig parentBobbinConfig, ScriptEngine scriptEngine) {
        super(destinationConfig, parentBobbinConfig, scriptEngine)
        instance = this
    }
}
