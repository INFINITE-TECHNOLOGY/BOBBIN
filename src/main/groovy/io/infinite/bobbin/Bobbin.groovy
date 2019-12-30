package io.infinite.bobbin

import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.destinations.Destination
import org.slf4j.helpers.MarkerIgnoringBase

class Bobbin extends MarkerIgnoringBase {

    String className

    List<Destination> destinations = new ArrayList<>()

    BobbinScriptEngine bobbinScriptEngine

    BobbinConfig bobbinConfig

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Bobbin(String className) {
        this.className = className
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    Boolean isLevelEnabled(Level level) {
        return bobbinConfig.isLevelEnabled(level.value())
    }

    Boolean isPackageEnabled(String className) {
        return bobbinConfig.isPackageEnabled(className)
    }

    Boolean isClassEnabled(String className) {
        return bobbinConfig.isClassEnabled(className)
    }

    Boolean isFiltered(Level level, String className) {
        return bobbinScriptEngine.isFiltered(level.value(), className)
    }

    Boolean needsLogging(Level level, String className) {
        return isLevelEnabled(level) && isPackageEnabled(className) && isClassEnabled(className) && (!isFiltered(level, className))
    }

    void log(Level level, String className, String msg) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.log(level, className, msg)
            }
        }
    }

    void logArg(Level level, String className, String format, Object arg) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.logArg(level, className, format, arg)
            }
        }
    }

    void logArgs(Level level, String className, String format, Object... arguments) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.logArgs(level, className, format, arguments)
            }
        }
    }

    void logArg1Arg2(Level level, String className, String format, Object arg1, Object arg2) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.logArg1Arg2(level, className, format, arg1, arg2)
            }
        }
    }

    void logThrowable(Level level, String className, String msg, Throwable t) {
        if (needsLogging(level, className)) {
            destinations.each {
                it.logThrowable(level, className, msg, t)
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
        logArg(Level.TRACE, className, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.TRACE, className, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        logArgs(Level.TRACE, className, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        logThrowable(Level.TRACE, className, msg, t)
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
        logArg(Level.DEBUG, className, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.DEBUG, className, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        logArgs(Level.DEBUG, className, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        logThrowable(Level.DEBUG, className, msg, t)
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
        logArg(Level.INFO, className, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.INFO, className, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        logArgs(Level.INFO, className, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        logThrowable(Level.INFO, className, msg, t)
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
        logArg(Level.WARN, className, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        logArgs(Level.WARN, className, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.WARN, className, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        logThrowable(Level.WARN, className, msg, t)
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
        logArg(Level.ERROR, className, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.ERROR, className, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        logArgs(Level.ERROR, className, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        logThrowable(Level.ERROR, className, msg, t)
    }

}
