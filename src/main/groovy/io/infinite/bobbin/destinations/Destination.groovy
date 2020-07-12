package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinEngine
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.AbstractDestinationConfig

abstract class Destination {

    AbstractDestinationConfig destinationConfig

    BobbinEngine bobbinEngine

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Destination(AbstractDestinationConfig destinationConfig) {
        this.destinationConfig = destinationConfig
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    void logMessage(String loggerName, Level level, String msg) {
        if (needsLogging(loggerName, level)) {
            String date = bobbinEngine.getDate()
            store(bobbinEngine.formatMessage(level.value(), loggerName, date, msg), loggerName, level, date)
        }
    }

    void logArg(String loggerName, Level level, String format, Object arg) {
        if (needsLogging(loggerName, level)) {
            String date = bobbinEngine.getDate()
            store(bobbinEngine.formatArg(level.value(), loggerName, date, format, arg), loggerName, level, date)
        }
    }

    void logArgs(String loggerName, Level level, String format, Object... arguments) {
        if (needsLogging(loggerName, level)) {
            String date = bobbinEngine.getDate()
            store(bobbinEngine.formatArgs(level.value(), loggerName, date, format, arguments), loggerName, level, date)
        }
    }

    void logArg1Arg2(String loggerName, Level level, String format, Object arg1, Object arg2) {
        if (needsLogging(loggerName, level)) {
            String date = bobbinEngine.getDate()
            store(bobbinEngine.formatArg1Arg2(level.value(), loggerName, date, format, arg1, arg2), loggerName, level, date)
        }
    }

    void logThrowable(String loggerName, Level level, String msg, Throwable t) {
        if (needsLogging(loggerName, level)) {
            String date = bobbinEngine.getDate()
            store(bobbinEngine.formatThrowable(level.value(), loggerName, date, msg, t), loggerName, level, date)
        }
    }

    abstract protected void store(String finalOutputMessageText, String loggerName, Level level, String date)

    Boolean needsLogging(String loggerName, Level level) {
        return destinationConfig.needsLogging(loggerName, level) &&
                (!bobbinEngine.isFiltered(level.value(), loggerName))
    }

}