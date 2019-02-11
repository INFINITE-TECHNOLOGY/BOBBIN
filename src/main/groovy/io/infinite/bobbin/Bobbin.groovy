package io.infinite.bobbin

import groovy.transform.CompileStatic
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.destinations.Destination
import org.slf4j.helpers.MarkerIgnoringBase

@CompileStatic
class Bobbin extends MarkerIgnoringBase {

    String className

    List<Destination> destinations = new ArrayList<>()

    BobbinScriptEngine bobbinScriptEngine
    
    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Bobbin(String className) {
        this.className = className
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    Boolean isLevelEnabled(Level level) {
        return bobbinScriptEngine.isLevelEnabled(level.value())
    }

    Boolean isClassEnabled(String className) {
        return bobbinScriptEngine.isClassEnabled(className)
    }

    Boolean needsLogging(Level level, String className) {
        return isLevelEnabled(level) && isClassEnabled(className)
    }

    void log(Level level, String className, String msg) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.log(level, className, msg)
            }
        }
    }

    void log(Level level, String className, String format, Object arg) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.log(level, className, format, arg)
            }
        }
    }

    void logWithArray(Level level, String className, String format, Object... arguments) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.logWithArray(level, className, format, arguments)
            }
        }
    }

    void log(Level level, String className, String format, Object arg1, Object arg2) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.log(level, className, format, arg1, arg2)
            }
        }
    }

    void log(Level level, String className, String msg, Throwable t) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.log(level, className, msg, t)
            }
        }
    }

    @Override
    boolean isTraceEnabled() {
        return needsLogging(Level.TRACE, className)
    }

    @Override
    void trace(String msg) {
        log(Level.TRACE, className, msg)
    }

    @Override
    void trace(String format, Object arg) {
        log(Level.TRACE, className, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        log(Level.TRACE, className, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        logWithArray(Level.TRACE, className, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        log(Level.TRACE, className, msg, t)
    }

    @Override
    boolean isDebugEnabled() {
        needsLogging(Level.DEBUG, className)
    }

    @Override
    void debug(String msg) {
        log(Level.DEBUG, className, msg)
    }

    @Override
    void debug(String format, Object arg) {
        log(Level.DEBUG, className, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        log(Level.DEBUG, className, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        logWithArray(Level.DEBUG, className, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        log(Level.DEBUG, className, msg, t)
    }

    @Override
    boolean isInfoEnabled() {
        needsLogging(Level.INFO, className)
    }

    @Override
    void info(String msg) {
        log(Level.INFO, className, msg)
    }

    @Override
    void info(String format, Object arg) {
        log(Level.INFO, className, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        log(Level.INFO, className, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        logWithArray(Level.INFO, className, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        log(Level.INFO, className, msg, t)
    }

    @Override
    boolean isWarnEnabled() {
        needsLogging(Level.WARN, className)
    }

    @Override
    void warn(String msg) {
        log(Level.WARN, className, msg)
    }

    @Override
    void warn(String format, Object arg) {
        log(Level.WARN, className, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        logWithArray(Level.WARN, className, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        log(Level.WARN, className, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        log(Level.WARN, className, msg, t)
    }

    @Override
    boolean isErrorEnabled() {
        needsLogging(Level.ERROR, className)
    }

    @Override
    void error(String msg) {
        log(Level.ERROR, className, msg)
    }

    @Override
    void error(String format, Object arg) {
        log(Level.ERROR, className, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        log(Level.ERROR, className, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        logWithArray(Level.ERROR, className, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        log(Level.ERROR, className, msg, t)
    }

}
