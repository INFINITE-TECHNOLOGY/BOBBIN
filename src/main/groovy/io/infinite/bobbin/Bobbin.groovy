package io.infinite.bobbin

import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.destinations.Destination
import org.slf4j.helpers.MarkerIgnoringBase

class Bobbin extends MarkerIgnoringBase {

    static BobbinConfig bobbinConfig = BobbinDestinationFactory.initBobbinConfig()

    static List<Destination> destinations = BobbinDestinationFactory.initDestinations(bobbinConfig)

    static BobbinEngine bobbinEngine = BobbinDestinationFactory.createBobbinEngine(bobbinConfig)

    String loggerName

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Bobbin(String loggerName) {
        this.loggerName = loggerName
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    void logMessage(Level level, String msg) {
        if (needsLogging(loggerName, level)) {
            destinations.each {
                it.logMessage(loggerName, level, msg)
            }
        }
    }

    void logArg(Level level, String format, Object arg) {
        if (needsLogging(loggerName, level)) {
            destinations.each {
                it.logArg(loggerName, level, format, arg)
            }
        }
    }

    void logArgs(Level level, String format, Object... arguments) {
        if (needsLogging(loggerName, level)) {
            destinations.each {
                it.logArgs(loggerName, level, format, arguments)
            }
        }
    }

    void logArg1Arg2(Level level, String format, Object arg1, Object arg2) {
        if (needsLogging(loggerName, level)) {
            destinations.each {
                it.logArg1Arg2(loggerName, level, format, arg1, arg2)
            }
        }
    }

    void logThrowable(Level level, String msg, Throwable t) {
        if (needsLogging(loggerName, level)) {
            destinations.each {
                it.logThrowable(loggerName, level, msg, t)
            }
        }
    }

    Boolean needsLogging(String loggerName, Level level) {
        return bobbinConfig.needsLogging(loggerName, level) &&
                (!bobbinEngine.isFiltered(level.value(), loggerName))
    }

    @Override
    boolean isTraceEnabled() {
        return true
    }

    @Override
    void trace(String msg) {
        logMessage(Level.TRACE, msg)
    }

    @Override
    void trace(String format, Object arg) {
        logArg(Level.TRACE, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.TRACE, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        logArgs(Level.TRACE, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        logThrowable(Level.TRACE, msg, t)
    }

    @Override
    boolean isDebugEnabled() {
        return true
    }

    @Override
    void debug(String msg) {
        logMessage(Level.DEBUG, msg)
    }

    @Override
    void debug(String format, Object arg) {
        logArg(Level.DEBUG, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.DEBUG, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        logArgs(Level.DEBUG, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        logThrowable(Level.DEBUG, msg, t)
    }

    @Override
    boolean isInfoEnabled() {
        return true
    }

    @Override
    void info(String msg) {
        logMessage(Level.INFO, msg)
    }

    @Override
    void info(String format, Object arg) {
        logArg(Level.INFO, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.INFO, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        logArgs(Level.INFO, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        logThrowable(Level.INFO, msg, t)
    }

    @Override
    boolean isWarnEnabled() {
        return true
    }

    @Override
    void warn(String msg) {
        logMessage(Level.WARN, msg)
    }

    @Override
    void warn(String format, Object arg) {
        logArg(Level.WARN, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        logArgs(Level.WARN, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.WARN, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        logThrowable(Level.WARN, msg, t)
    }

    @Override
    boolean isErrorEnabled() {
        return true
    }

    @Override
    void error(String msg) {
        logMessage(Level.ERROR, msg)
    }

    @Override
    void error(String format, Object arg) {
        logArg(Level.ERROR, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        logArg1Arg2(Level.ERROR, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        logArgs(Level.ERROR, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        logThrowable(Level.ERROR, msg, t)
    }

}
