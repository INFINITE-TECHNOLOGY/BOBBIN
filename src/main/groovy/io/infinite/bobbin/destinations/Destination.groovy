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
        scriptEngine.put("event", event)
        scriptEngine.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        scriptEngine.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
        scriptEngine.put("level", event.getLevel().value())
        scriptEngine.put("className", event.getClassName())
        scriptEngine.put("MDC", MDC)
        scriptEngine.put("all", true)
        scriptEngine.put("none", false)
        scriptEngine.put("threadName", Thread.currentThread().getName())
        scriptEngine.put("threadGroupName", Thread.currentThread().getThreadGroup().getName())
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

    //@Memoized(maxCacheSize = 128)
    final Boolean needsLogging(Level level, String className) {
        return (scriptEngine.eval(destinationConfig.classes ?: parentBobbinConfig.classes)
        && scriptEngine.eval(destinationConfig.levels ?: parentBobbinConfig.levels))
    }

    final Event formatMessage(Event event) {
        if (event.throwable != null) {
            event.setError(scriptEngine.eval(destinationConfig.errorFormat) as String)
        } else {
            event.setError("")
        }
        event.setFormattedMessage(scriptEngine.eval(destinationConfig.format) as String)
        return event
    }

    Destination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        this.destinationConfig = destinationConfig
        this.parentBobbinConfig = parentBobbinConfig
    }

}