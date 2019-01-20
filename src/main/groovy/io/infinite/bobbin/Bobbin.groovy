package io.infinite.bobbin

import groovy.transform.Memoized
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.destinations.Destination
import org.slf4j.spi.MDCAdapter

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class Bobbin implements MDCAdapter {

    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy")

    BobbinConfig bobbinConfig

    Map<String, String> contextMap = [:]

    String get(String key) {
        return contextMap.get(key)
    }

    void put(String key, String value) {
        contextMap.put(key, value)
    }

    @Override
    void remove(String key) {
        contextMap.remove(key)
    }

    @Override
    void clear() {
        contextMap.clear()
    }

    @Override
    Map<String, String> getCopyOfContextMap() {
        return contextMap.clone() as Map<String, String>
    }

    @Override
    void setContextMap(Map<String, String> contextMap) {
        this.contextMap = contextMap
    }

    //@Memoized
    Boolean isLevelEnabled(Level level) {
        scriptEngine.put("level", level.value())
        return scriptEngine.eval(bobbinConfig.levels)
    }

    //@Memoized(maxCacheSize = 128)
    final Boolean isClassEnabled(String className) {
        scriptEngine.put("className", className)
        return scriptEngine.eval(bobbinConfig.classes)
    }

    //@Memoized(maxCacheSize = 128)
    final Boolean isLevelAndClassEnabled(Level level, String className) {
        return isLevelEnabled(level) && isClassEnabled(className)
    }

    Bobbin(BobbinConfig bobbinConfig) {
        scriptEngine.put("all", true)
        scriptEngine.put("none", false)
        scriptEngine.put("threadName", Thread.currentThread().getName())
        scriptEngine.put("threadGroupName", Thread.currentThread().getThreadGroup().getName())
        scriptEngine.put("bobbin", this)
        this.bobbinConfig = bobbinConfig
        bobbinConfig.destinations.each {
            Destination destination = Class.forName(it.name).newInstance(
                    it,
                    bobbinConfig
            ) as Destination
            destinations.add(destination)
        }
    }

    List<Destination> destinations = new ArrayList<>()

    void log(Event event) {
        destinations.each { it.log(event) }
    }

    boolean isTraceEnabled(String className) {
        return isLevelAndClassEnabled(Level.TRACE, className)
    }

    void trace(String className, String msg) {
        log(new Event(className: className, level: Level.TRACE, message: msg))
    }

    void trace(String className, String format, Object arg) {
        log(new Event(className: className, level: Level.TRACE, message: format, arguments: [arg]))
    }

    void trace(String className, String format, Object arg1, Object arg2) {
        log(new Event(className: className, level: Level.TRACE, message: format, arguments: [arg1, arg2]))
    }

    void trace(String className, String format, Object... arguments) {
        log(new Event(className: className, level: Level.TRACE, message: format, arguments: arguments))
    }

    void trace(String className, String msg, Throwable t) {
        log(new Event(className: className, level: Level.TRACE, message: msg, throwable: t))
    }

    boolean isDebugEnabled(String name) {
        return isLevelAndClassEnabled(Level.DEBUG, name)
    }

    void debug(String className, String msg) {
        log(new Event(className: className, level: Level.DEBUG, message: msg))
    }

    void debug(String className, String format, Object arg) {
        log(new Event(className: className, level: Level.DEBUG, message: format, arguments: [arg]))
    }

    void debug(String className, String format, Object arg1, Object arg2) {
        log(new Event(className: className, level: Level.DEBUG, message: format, arguments: [arg1, arg2]))
    }

    void debug(String className, String format, Object... arguments) {
        log(new Event(className: className, level: Level.DEBUG, message: format, arguments: arguments))
    }

    void debug(String className, String msg, Throwable t) {
        log(new Event(className: className, level: Level.DEBUG, message: msg, throwable: t))
    }

    boolean isInfoEnabled(String name) {
        return isLevelAndClassEnabled(Level.INFO, name)
    }

    void info(String className, String msg) {
        log(new Event(className: className, level: Level.INFO, message: msg))
    }

    void info(String className, String format, Object arg) {
        log(new Event(className: className, level: Level.INFO, message: format, arguments: [arg]))
    }

    void info(String className, String format, Object arg1, Object arg2) {
        log(new Event(className: className, level: Level.INFO, message: format, arguments: [arg1, arg2]))
    }

    void info(String className, String format, Object... arguments) {
        log(new Event(className: className, level: Level.INFO, message: format, arguments: arguments))
    }

    void info(String className, String msg, Throwable t) {
        log(new Event(className: className, level: Level.INFO, message: msg, throwable: t))
    }

    boolean isWarnEnabled(String name) {
        return isLevelAndClassEnabled(Level.WARN, name)
    }

    void warn(String className, String msg) {
        log(new Event(className: className, level: Level.WARN, message: msg))
    }

    void warn(String className, String format, Object arg) {
        log(new Event(className: className, level: Level.WARN, message: format, arguments: [arg]))
    }

    void warn(String className, String format, Object... arguments) {
        log(new Event(className: className, level: Level.WARN, message: format, arguments: arguments))
    }

    void warn(String className, String format, Object arg1, Object arg2) {
        log(new Event(className: className, level: Level.WARN, message: format, arguments: [arg1, arg2]))
    }

    void warn(String className, String msg, Throwable t) {
        log(new Event(className: className, level: Level.WARN, message: msg, throwable: t))
    }

    boolean isErrorEnabled(String name) {
        return isLevelAndClassEnabled(Level.ERROR, name)
    }

    void error(String className, String msg) {
        log(new Event(className: className, level: Level.ERROR, message: msg))
    }

    void error(String className, String format, Object arg) {
        log(new Event(className: className, level: Level.ERROR, message: format, arguments: [arg]))
    }

    void error(String className, String format, Object arg1, Object arg2) {
        log(new Event(className: className, level: Level.ERROR, message: format, arguments: [arg1, arg2]))
    }

    void error(String className, String format, Object... arguments) {
        log(new Event(className: className, level: Level.ERROR, message: format, arguments: arguments))
    }

    void error(String className, String msg, Throwable t) {
        log(new Event(className: className, level: Level.ERROR, message: msg, throwable: t))
    }

}