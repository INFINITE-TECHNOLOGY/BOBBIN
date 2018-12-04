package io.infinite.bobbin.destinations

import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.text.SimpleDateFormat

abstract class Destination {

    BobbinConfig.Destination destinationConfig

    BobbinConfig parentBobbinConfig

    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy")

    final void log(Event event) {
        scriptEngine.put("event", event)
        scriptEngine.put("level", event.getLevel().value())
        scriptEngine.put("className", event.getClassName())
        scriptEngine.put("threadName", Thread.currentThread().getName())
        scriptEngine.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        scriptEngine.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
        scriptEngine.put("all", true)
        if (!isEventEnabled()) {
            return
        }
        if (!isClassEnabled()) {
            return
        }
        formatMessage(event)
        store(event)
    }

    abstract protected void store(Event event)

    final Boolean isEventEnabled() {
        return scriptEngine.eval(destinationConfig.levels?:parentBobbinConfig.levels)
    }

    final Boolean isClassEnabled() {
        return scriptEngine.eval(destinationConfig.classes?:parentBobbinConfig.classes)
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