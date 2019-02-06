package io.infinite.bobbin.destinations

import groovy.transform.Memoized
import io.infinite.bobbin.Event
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.MDC

import javax.script.Bindings
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.text.SimpleDateFormat

abstract class Destination {

    DestinationConfig destinationConfig

    BobbinConfig parentBobbinConfig

    ScriptEngine scriptEngine = new ScriptEngineManager(this.getClass().getClassLoader()).getEngineByName("groovy")

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Destination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        this.destinationConfig = destinationConfig
        this.parentBobbinConfig = parentBobbinConfig
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    String toString() {
        return super.toString()
    }

    void commonBinding1(Event event) {
        scriptEngine.put("event", event)
        scriptEngine.put("level", event.getLevel().value())
        scriptEngine.put("className", event.getClassName())
        scriptEngine.put("MDC", MDC)
        scriptEngine.put("all", true)
        scriptEngine.put("none", false)
        scriptEngine.put("threadName", Thread.currentThread().getName())
        scriptEngine.put("threadGroupName", Thread.currentThread().getThreadGroup().getName())
    }

    void commonBinding2(Event event) {
        scriptEngine.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        scriptEngine.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
    }

    final void log(Event event) {
        commonBinding1(event)
        if (!needsLogging(event.getLevel(), event.getClassName())) {
            return
        }
        commonBinding2(event)
        formatMessage(event)
        store(event)
    }

    abstract protected void store(Event event)

    @Memoized(maxCacheSize = 128)
    synchronized final Boolean needsLogging(Level level, String className) {
        return (scriptEngine.eval(destinationConfig.classes ?: parentBobbinConfig.classes)
                && scriptEngine.eval(destinationConfig.levels ?: parentBobbinConfig.levels))
    }

    synchronized final Event formatMessage(Event event) {
        if (event.throwable != null) {
            event.setError(scriptEngine.eval(destinationConfig.errorFormat ?: parentBobbinConfig.errorFormat) as String)
        } else {
            event.setError("")
        }
        event.setFormattedMessage(scriptEngine.eval(destinationConfig.format ?: parentBobbinConfig.format) as String)
        return event
    }

}