package io.infinite.bobbin

import io.infinite.bobbin.destinations.Destination
import org.slf4j.Logger

class TestBobbinFactory extends BobbinFactory {

    @Override
    Logger getLogger(String name) {
        Bobbin bobbin = new Bobbin(name)
        bobbinConfig.destinations.each {
            Destination destination = Class.forName(it.name).newInstance(
                    it
            ) as Destination
            destination.bobbinScriptEngine = new TestBobbinScriptEngineFactory(bobbinConfig).getDestinationBobbinScriptEngine(it)
            bobbin.destinations.add(destination)
        }
        bobbin.bobbinScriptEngine = new TestBobbinScriptEngineFactory(bobbinConfig).bobbinScriptEngine
        return bobbin
    }
}
