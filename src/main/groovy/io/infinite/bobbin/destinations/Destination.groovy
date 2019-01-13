package io.infinite.bobbin.destinations

import groovy.transform.Memoized
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.Event
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.MDC

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.text.SimpleDateFormat

abstract class Destination {

    DestinationConfig destinationConfig

    BobbinConfig parentBobbinConfig

    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy")

    void commonBinding(Event event) {
        getScriptEngine().put("event", event)
        getScriptEngine().put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        getScriptEngine().put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
        getScriptEngine().put("level", event.getLevel().value())
        getScriptEngine().put("className", event.getClassName())
        getScriptEngine().put("MDC", MDC)
        getScriptEngine().put("all", true)
        getScriptEngine().put("none", false)
        getScriptEngine().put("threadName", Thread.currentThread().getName())
    }

    final void log(Event event) {
        commonBinding(event)
        if (!needsLogging(event.getLevel(), event.getClassName())) {
            return
        }
        formatMessage(event)
        store(event)
    }

    abstract protected void store(Event event)

    @Memoized(maxCacheSize = 128)
    final Boolean needsLogging(Level level, String className) {
        return (scriptEngine.eval(destinationConfig.classes ?: parentBobbinConfig.classes)
        && scriptEngine.eval(destinationConfig.levels ?: parentBobbinConfig.levels))
    }

    final Event formatMessage(Event event) {
        event.setFormattedMessage(scriptEngine.eval(destinationConfig.format) as String)
        return event
    }

    Destination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        this.destinationConfig = destinationConfig
        this.parentBobbinConfig = parentBobbinConfig
    }

}