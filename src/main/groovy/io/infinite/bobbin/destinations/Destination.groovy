package io.infinite.bobbin.destinations

import groovy.transform.CompileStatic
import groovy.transform.Memoized
import io.infinite.bobbin.BobbinScriptEngine
import io.infinite.bobbin.BobbinScriptEngineFactory
import io.infinite.bobbin.Event
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig

@CompileStatic
abstract class Destination {

    DestinationConfig destinationConfig

    BobbinConfig parentBobbinConfig

    BobbinScriptEngine bobbinScriptEngine

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Destination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        this.destinationConfig = destinationConfig
        this.parentBobbinConfig = parentBobbinConfig
        this.bobbinScriptEngine = new BobbinScriptEngineFactory().getDestinationBobbinScriptEngine(destinationConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    String toString() {
        return super.toString()
    }

    void log(Event event) {
        if (!needsLogging(event.getLevel(), event.getClassName())) {
            return
        }
        formatMessage(event)
        store(event)
    }

    abstract protected void store(Event event)

    @Memoized(maxCacheSize = 128)
    Boolean needsLogging(Level level, String className) {
        return (bobbinScriptEngine.isClassEnabled(className)
                && bobbinScriptEngine.isLevelEnabled(level.value()))
    }

    Event formatMessage(Event event) {
        /*if (event.throwable != null) {
            event.setError(scriptEngine.eval(destinationConfig.errorFormat ?: parentBobbinConfig.errorFormat) as String)
        } else {
            event.setError("")
        }*/
        event.setFormattedMessage(bobbinScriptEngine.formatMessage(event) as String)
        return event
    }

}