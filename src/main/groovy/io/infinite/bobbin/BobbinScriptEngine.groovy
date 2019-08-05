package io.infinite.bobbin

import org.apache.commons.lang3.time.FastDateFormat

abstract class BobbinScriptEngine {

    final Boolean all = true

    final Boolean none = false

    String code

    FastDateFormat dateFormat = FastDateFormat.getInstance(getDateFormat())

    FastDateFormat dateTimeFormat = FastDateFormat.getInstance(getDateTimeFormat())

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    BobbinScriptEngine(String code) {
        this.code = code
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    String getThreadName() {
        return Thread.currentThread().getName()
    }

    String getThreadGroupName() {
        return Thread.currentThread().getThreadGroup().getName()
    }

    Class<org.slf4j.MDC> getMDC() {//keep full qualified reference for MDC getter shortcut
        return org.slf4j.MDC
    }

    String getDate() {
        return dateFormat.format(new Date())
    }

    String getDateTime() {
        return dateTimeFormat.format(new Date())
    }

    abstract Boolean isLevelEnabled(String level)

    abstract Boolean isClassEnabled(String className)

    abstract String evalFileName(String level, String className, String date)

    abstract String formatLine(String level, String className, String date, String msg)

    abstract String formatLineArg(String level, String className, String date, String format, Object arg)

    abstract String formatLineArgs(String level, String className, String date, String format, Object... arguments)

    abstract String formatLineArg1Arg2(String level, String className, String date, String format, Object arg1, Object arg2)

    abstract String formatLineThrowable(String level, String className, String date, String msg, Throwable t)

    abstract String getDateFormat()

    abstract String getDateTimeFormat()

}