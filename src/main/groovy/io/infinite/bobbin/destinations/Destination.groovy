package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinScriptEngine
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig

abstract class Destination {

    DestinationConfig destinationConfig

    BobbinScriptEngine bobbinScriptEngine

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    Destination(DestinationConfig destinationConfig) {
        this.destinationConfig = destinationConfig
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    void log(Level level, String className, String msg) {
        if (needsLogging(level, className)) {
            String date = bobbinScriptEngine.getDate()
            store(bobbinScriptEngine.formatLine(level.value(), className, date, msg), level, className, date)
        }
    }

    void logArg(Level level, String className, String format, Object arg) {
        if (needsLogging(level, className)) {
            String date = bobbinScriptEngine.getDate()
            store(bobbinScriptEngine.formatLineArg(level.value(), className, date, format, arg), level, className, date)
        }
    }

    void logArgs(Level level, String className, String format, Object... arguments) {
        if (needsLogging(level, className)) {
            String date = bobbinScriptEngine.getDate()
            store(bobbinScriptEngine.formatLineArgs(level.value(), className, date, format, arguments), level, className, date)
        }
    }

    void logArg1Arg2(Level level, String className, String format, Object arg1, Object arg2) {
        if (needsLogging(level, className)) {
            String date = bobbinScriptEngine.getDate()
            store(bobbinScriptEngine.formatLineArg1Arg2(level.value(), className, date, format, arg1, arg2), level, className, date)
        }
    }

    void logThrowable(Level level, String className, String msg, Throwable t) {
        if (needsLogging(level, className)) {
            String date = bobbinScriptEngine.getDate()
            store(bobbinScriptEngine.formatLineThrowable(level.value(), className, date, msg, t), level, className, date)
        }
    }

    abstract protected void store(String finalOutputMessageText, Level level, String className, String date)

    Boolean needsLogging(Level level, String className) {
        return (bobbinScriptEngine.isClassEnabled(className)
                && bobbinScriptEngine.isLevelEnabled(level.value()))
    }

}