package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.Event

import java.text.SimpleDateFormat

abstract class Destination {

    BobbinConfig.Destination destinationConfig

    Binding binding = new Binding()
    GroovyShell groovyShell = new GroovyShell(binding)

    final void log(Event event) {
        binding.setProperty("event", event)
        binding.setProperty("level", event.getLevel().value())
        binding.setProperty("className", event.getClassName())
        binding.setProperty("threadName", Thread.currentThread().getName())
        binding.setProperty("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        binding.setProperty("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
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
        return groovyShell.evaluate(destinationConfig.levels)
    }

    final Boolean isClassEnabled() {
        return groovyShell.evaluate(destinationConfig.classes)
    }

    final Event formatMessage(Event event) {
        event.setFormattedMessage(groovyShell.evaluate(destinationConfig.format) as String)
        return event
    }

    final void setDestinationConfig(BobbinConfig.Destination destinationConfig) {
        this.destinationConfig = destinationConfig
    }

}