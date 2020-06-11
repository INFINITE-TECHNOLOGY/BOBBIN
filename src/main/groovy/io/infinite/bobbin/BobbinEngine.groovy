package io.infinite.bobbin


import io.infinite.supplies.ast.exceptions.ExceptionUtils
import org.apache.commons.lang3.time.FastDateFormat

abstract class BobbinEngine {

    final Boolean all = true

    final Boolean none = false

    FastDateFormat dateFormat = FastDateFormat.getInstance(getDateFormat())

    FastDateFormat dateTimeFormat = FastDateFormat.getInstance(getDateTimeFormat())

    ExceptionUtils exceptionUtils = new ExceptionUtils()

    String getThreadName() {
        return Thread.currentThread().name
    }

    String getThreadGroupName() {
        return Thread.currentThread().getThreadGroup().name
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

    abstract Boolean isFiltered(String level, String className)

    abstract String evalFileName(String level, String className, String date)

    abstract String formatMessage(String level, String className, String date, String msg)

    abstract String formatArg(String level, String className, String date, String format, Object arg)

    abstract String formatArgs(String level, String className, String date, String format, Object... arguments)

    abstract String formatArg1Arg2(String level, String className, String date, String format, Object arg1, Object arg2)

    abstract String formatThrowable(String level, String className, String date, String msg, Throwable t)

    abstract String getDateFormat()

    abstract String getDateTimeFormat()

}