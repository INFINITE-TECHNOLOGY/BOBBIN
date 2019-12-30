package io.infinite.bobbin

import io.infinite.bobbin.destinations.Destination
import org.slf4j.helpers.MarkerIgnoringBase

class Bobbin extends MarkerIgnoringBase {

    List<Destination> destinations
    
    String loggerName

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Bobbin(String loggerName, List<Destination> destinations) {
        this.loggerName= loggerName
        this.destinations = destinations
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    void log(Level level, String msg) {
        destinations.each {
            it.log(loggerName, level, msg)
        }
    }

    void logArg(Level level, String format, Object arg) {
        destinations.each {
            it.logArg(loggerName, level, format, arg)
        }
    }

    void logArgs(Level level, String format, Object... arguments) {
        destinations.each {
            it.logArgs(loggerName, level, format, arguments)
        }
    }

    void logArg1Arg2(Level level, String format, Object arg1, Object arg2) {
        destinations.each {
            it.logArg1Arg2(loggerName, level, format, arg1, arg2)
        }
    }

    void logThrowable(Level level, String msg, Throwable t) {
        destinations.each {
            it.logThrowable(loggerName, level, msg, t)
        }
    }

    @Override
    boolean isTraceEnabled() {
        return true
    }

    @Override
    void trace(String msg) {
        log(Level.TRACE, msg)
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
        log(Level.DEBUG, msg)
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
        log(Level.INFO, msg)
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
        log(Level.WARN, msg)
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
        log(Level.ERROR, msg)
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
