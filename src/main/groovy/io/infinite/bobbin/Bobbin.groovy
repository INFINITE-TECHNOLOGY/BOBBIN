package io.infinite.bobbin

import groovy.transform.Memoized
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.destinations.Destination
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl

import javax.script.Bindings
import javax.script.CompiledScript
import javax.script.ScriptEngineManager

class Bobbin {

    CompiledScript levelsScript

    CompiledScript classesScript

    Bindings bindings

    private BobbinConfig bobbinConfig

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Bobbin(BobbinConfig bobbinConfig) {
        setBobbinConfig(bobbinConfig)
        bobbinConfig.destinations.each {
            Destination destination = Class.forName(it.name).newInstance(
                    it,
                    bobbinConfig
            ) as Destination
            destinations.add(destination)
        }
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    void compileScripts() {
        GroovyScriptEngineImpl scriptEngine = new ScriptEngineManager(this.getClass().getClassLoader()).getEngineByName("groovy") as GroovyScriptEngineImpl
        levelsScript = scriptEngine.compile(bobbinConfig.levels)
        classesScript = scriptEngine.compile(bobbinConfig.classes)
        bindings = scriptEngine.createBindings()
    }

    void setBobbinConfig(BobbinConfig bobbinConfig) {
        this.bobbinConfig = bobbinConfig
        compileScripts()
    }

    BobbinConfig getBobbinConfig() {
        return bobbinConfig
    }

    @Memoized
    Boolean isLevelEnabled(Level level) {
        commonBinding()
        bindings.put("level", level.value())
        return levelsScript.eval(bindings)
    }

    @Memoized(maxCacheSize = 128)
    final Boolean isClassEnabled(String className) {
        commonBinding()
        bindings.put("className", className)
        return classesScript.eval(bindings)
    }

    @Memoized(maxCacheSize = 128)
    final Boolean isLevelAndClassEnabled(Level level, String className) {
        return isLevelEnabled(level) && isClassEnabled(className)
    }

    void commonBinding() {
        bindings.put("all", true)
        bindings.put("none", false)
        bindings.put("threadName", Thread.currentThread().getName())
        bindings.put("threadGroupName", Thread.currentThread().getThreadGroup().getName())
        bindings.put("bobbin", this)
    }

    List<Destination> destinations = new ArrayList<>()

    void log(Event event) {
        destinations.each { it.log(event) }
    }

    boolean isTraceEnabled(String className) {
        return isLevelAndClassEnabled(Level.TRACE, className)
    }

    void trace(String className, String msg) {
        if (isTraceEnabled(className)) {
            log(new Event(className: className, level: Level.TRACE, message: msg))
        }
    }

    void trace(String className, String format, Object arg) {
        if (isTraceEnabled(className)) {
            log(new Event(className: className, level: Level.TRACE, message: format, arguments: [arg]))
        }
    }

    void trace(String className, String format, Object arg1, Object arg2) {
        if (isTraceEnabled(className)) {
            log(new Event(className: className, level: Level.TRACE, message: format, arguments: [arg1, arg2]))
        }
    }

    void trace(String className, String format, Object... arguments) {
        if (isTraceEnabled(className)) {
            log(new Event(className: className, level: Level.TRACE, message: format, arguments: arguments))
        }
    }

    void trace(String className, String msg, Throwable t) {
        if (isTraceEnabled(className)) {
            log(new Event(className: className, level: Level.TRACE, message: msg, throwable: t))
        }
    }

    boolean isDebugEnabled(String className) {
        return isLevelAndClassEnabled(Level.DEBUG, className)
    }

    void debug(String className, String msg) {
        if (isDebugEnabled(className)) {
            log(new Event(className: className, level: Level.DEBUG, message: msg))
        }
    }

    void debug(String className, String format, Object arg) {
        if (isDebugEnabled(className)) {
            log(new Event(className: className, level: Level.DEBUG, message: format, arguments: [arg]))
        }
    }

    void debug(String className, String format, Object arg1, Object arg2) {
        if (isDebugEnabled(className)) {
            log(new Event(className: className, level: Level.DEBUG, message: format, arguments: [arg1, arg2]))
        }
    }

    void debug(String className, String format, Object... arguments) {
        if (isDebugEnabled(className)) {
            log(new Event(className: className, level: Level.DEBUG, message: format, arguments: arguments))
        }
    }

    void debug(String className, String msg, Throwable t) {
        if (isDebugEnabled(className)) {
            log(new Event(className: className, level: Level.DEBUG, message: msg, throwable: t))
        }
    }

    boolean isInfoEnabled(String className) {
        return isLevelAndClassEnabled(Level.INFO, className)
    }

    void info(String className, String msg) {
        if (isInfoEnabled(className)) {
            log(new Event(className: className, level: Level.INFO, message: msg))
        }
    }

    void info(String className, String format, Object arg) {
        if (isInfoEnabled(className)) {
            log(new Event(className: className, level: Level.INFO, message: format, arguments: [arg]))
        }
    }

    void info(String className, String format, Object arg1, Object arg2) {
        if (isInfoEnabled(className)) {
            log(new Event(className: className, level: Level.INFO, message: format, arguments: [arg1, arg2]))
        }
    }

    void info(String className, String format, Object... arguments) {
        if (isInfoEnabled(className)) {
            log(new Event(className: className, level: Level.INFO, message: format, arguments: arguments))
        }
    }

    void info(String className, String msg, Throwable t) {
        if (isInfoEnabled(className)) {
            log(new Event(className: className, level: Level.INFO, message: msg, throwable: t))
        }
    }

    boolean isWarnEnabled(String className) {
        return isLevelAndClassEnabled(Level.WARN, className)
    }

    void warn(String className, String msg) {
        if (isWarnEnabled(className)) {
            log(new Event(className: className, level: Level.WARN, message: msg))
        }
    }

    void warn(String className, String format, Object arg) {
        if (isWarnEnabled(className)) {
            log(new Event(className: className, level: Level.WARN, message: format, arguments: [arg]))
        }
    }

    void warn(String className, String format, Object... arguments) {
        if (isWarnEnabled(className)) {
            log(new Event(className: className, level: Level.WARN, message: format, arguments: arguments))
        }
    }

    void warn(String className, String format, Object arg1, Object arg2) {
        if (isWarnEnabled(className)) {
            log(new Event(className: className, level: Level.WARN, message: format, arguments: [arg1, arg2]))
        }
    }

    void warn(String className, String msg, Throwable t) {
        if (isWarnEnabled(className)) {
            log(new Event(className: className, level: Level.WARN, message: msg, throwable: t))
        }
    }

    boolean isErrorEnabled(String className) {
        return isLevelAndClassEnabled(Level.ERROR, className)
    }

    void error(String className, String msg) {
        if (isErrorEnabled(className)) {
            log(new Event(className: className, level: Level.ERROR, message: msg))
        }
    }

    void error(String className, String format, Object arg) {
        if (isErrorEnabled(className)) {
            log(new Event(className: className, level: Level.ERROR, message: format, arguments: [arg]))
        }
    }

    void error(String className, String format, Object arg1, Object arg2) {
        if (isErrorEnabled(className)) {
            log(new Event(className: className, level: Level.ERROR, message: format, arguments: [arg1, arg2]))
        }
    }

    void error(String className, String format, Object... arguments) {
        if (isErrorEnabled(className)) {
            log(new Event(className: className, level: Level.ERROR, message: format, arguments: arguments))
        }
    }

    void error(String className, String msg, Throwable t) {
        if (isErrorEnabled(className)) {
            log(new Event(className: className, level: Level.ERROR, message: msg, throwable: t))
        }
    }

}