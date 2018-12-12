package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event

import javax.script.ScriptEngine

class ConsoleDestination extends Destination {

    ConsoleDestination(BobbinConfig.Destination destinationConfig, BobbinConfig parentBobbinConfig, ScriptEngine scriptEngine) {
        super(destinationConfig, parentBobbinConfig, scriptEngine)
    }

    @Override
    protected void store(Event event) {
        System.out.print(event.getFormattedMessage())
    }
}
