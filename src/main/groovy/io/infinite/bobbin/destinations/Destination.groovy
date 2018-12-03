package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.text.SimpleDateFormat

abstract class Destination {

    BobbinConfig.Destination destinationConfig

    ScriptEngine scriptingEngine = new ScriptEngineManager().getEngineByName("groovy")

    final void log(Event event) {
        scriptingEngine.put("event", event)
        scriptingEngine.put("level", event.getLevel().value())
        scriptingEngine.put("className", event.getClassName())
        scriptingEngine.put("threadName", Thread.currentThread().getName())
        scriptingEngine.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        scriptingEngine.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
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
        return scriptingEngine.eval(destinationConfig.levels)
    }

    final Boolean isClassEnabled() {
        return scriptingEngine.eval(destinationConfig.classes)
    }

    final Event formatMessage(Event event) {
        event.setFormattedMessage(scriptingEngine.eval(destinationConfig.format) as String)
        return event
    }

    final void setDestinationConfig(BobbinConfig.Destination destinationConfig) {
        this.destinationConfig = destinationConfig
    }

}