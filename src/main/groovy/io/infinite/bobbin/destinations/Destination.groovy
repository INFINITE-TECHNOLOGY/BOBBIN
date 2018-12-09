package io.infinite.bobbin.destinations

import groovy.transform.Memoized
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event
import io.infinite.bobbin.Level

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.text.SimpleDateFormat

abstract class Destination {

    BobbinConfig.Destination destinationConfig

    BobbinConfig parentBobbinConfig

    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy")

    final void log(Event event) {
        scriptEngine.put("event", event)
        scriptEngine.put("threadName", Thread.currentThread().getName())
        scriptEngine.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        scriptEngine.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
        scriptEngine.put("level", event.getLevel().value())
        scriptEngine.put("className", event.getClassName())
        scriptEngine.put("all", true)
        if (!isLevelAndClassEnabled(event.getLevel(), event.getClassName())) {
            return
        }
        formatMessage(event)
        store(event)
    }

    abstract protected void store(Event event)

    @Memoized(maxCacheSize = 128)
    final Boolean isLevelAndClassEnabled(Level level, String className) {
        return isLevelEnabled(level) && isClassEnabled(className)
    }

    @Memoized
    final Boolean isLevelEnabled(Level level) {
        scriptEngine.put("level", level.value())
        scriptEngine.put("all", true)
        return scriptEngine.eval(destinationConfig.levels ?: parentBobbinConfig.levels)
    }

    @Memoized(maxCacheSize = 128)
    final Boolean isClassEnabled(String className) {
        scriptEngine.put("className", className)
        return scriptEngine.eval(destinationConfig.classes ?: parentBobbinConfig.classes)
    }

    final Event formatMessage(Event event) {
        event.setFormattedMessage(scriptEngine.eval(destinationConfig.format) as String)
        return event
    }

    final void setConfigs(BobbinConfig.Destination destinationConfig, BobbinConfig parentBobbinConfig) {
        this.destinationConfig = destinationConfig
        this.parentBobbinConfig = parentBobbinConfig
    }

}