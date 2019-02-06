package io.infinite.bobbin.destinations

import groovy.transform.Memoized
import io.infinite.bobbin.Event
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl
import org.slf4j.MDC

import javax.script.Bindings
import javax.script.CompiledScript
import javax.script.ScriptEngineManager
import java.text.SimpleDateFormat

abstract class Destination {

    private DestinationConfig destinationConfig

    private BobbinConfig parentBobbinConfig

    Bindings bindings

    CompiledScript levelsScript

    CompiledScript classesScript

    CompiledScript formatScript

    CompiledScript errorFormatScript

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Destination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        setDestinationConfig(destinationConfig)
        setParentBobbinConfig(parentBobbinConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    void compileScripts() {
        GroovyScriptEngineImpl scriptEngine = new ScriptEngineManager(this.getClass().getClassLoader()).getEngineByName("groovy") as GroovyScriptEngineImpl
        levelsScript = scriptEngine.compile(destinationConfig.levels ?: parentBobbinConfig.levels)
        classesScript = scriptEngine.compile(destinationConfig.classes ?: parentBobbinConfig.classes)
        formatScript = scriptEngine.compile(destinationConfig.format ?: parentBobbinConfig.format)
        errorFormatScript = scriptEngine.compile(destinationConfig.errorFormat ?: parentBobbinConfig.errorFormat)
        bindings = scriptEngine.createBindings()
    }

    DestinationConfig getDestinationConfig() {
        return destinationConfig
    }

    void setDestinationConfig(DestinationConfig destinationConfig) {
        this.destinationConfig = destinationConfig
        compileScripts()
    }

    BobbinConfig getParentBobbinConfig() {
        return parentBobbinConfig
    }

    void setParentBobbinConfig(BobbinConfig parentBobbinConfig) {
        this.parentBobbinConfig = parentBobbinConfig
        compileScripts()
    }

    @Override
    String toString() {
        return super.toString()
    }

    void commonBinding1(Event event) {
        bindings.put("event", event)
        bindings.put("level", event.getLevel().value())
        bindings.put("className", event.getClassName())
        bindings.put("MDC", MDC)
        bindings.put("all", true)
        bindings.put("none", false)
        bindings.put("threadName", Thread.currentThread().getName())
        bindings.put("threadGroupName", Thread.currentThread().getThreadGroup().getName())
    }

    void commonBinding2(Event event) {
        bindings.put("date", new SimpleDateFormat(destinationConfig.dateFormat).format(event.getDate()))
        bindings.put("dateTime", new SimpleDateFormat(destinationConfig.dateTimeFormat).format(event.getDate()))
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
    final Boolean needsLogging(Level level, String className) {
        return (classesScript.eval(bindings)
                && levelsScript.eval(bindings))
    }

    final Event formatMessage(Event event) {
        if (event.throwable != null) {
            event.setError(errorFormatScript.eval(bindings) as String)
        } else {
            event.setError("")
        }
        event.setFormattedMessage(formatScript.eval(bindings) as String)
        return event
    }

}