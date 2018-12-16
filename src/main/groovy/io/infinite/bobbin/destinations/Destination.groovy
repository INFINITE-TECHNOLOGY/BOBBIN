package io.infinite.bobbin.destinations

import groovy.transform.Memoized
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event
import io.infinite.bobbin.Level
import org.slf4j.MDC

import javax.script.ScriptEngine
import java.text.SimpleDateFormat

abstract class Destination {

    BobbinConfig.Destination destinationConfig

    BobbinConfig parentBobbinConfig

    ScriptEngine scriptEngine

    final void log(Event event) {
        scriptEngine.put("event", event)
        scriptEngine.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        scriptEngine.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
        scriptEngine.put("level", event.getLevel().value())
        scriptEngine.put("className", event.getClassName())
        scriptEngine.put("MDC", MDC)
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

    Destination(BobbinConfig.Destination destinationConfig, BobbinConfig parentBobbinConfig, ScriptEngine scriptEngine) {
        this.destinationConfig = destinationConfig
        this.parentBobbinConfig = parentBobbinConfig
        this.scriptEngine = scriptEngine
    }

}