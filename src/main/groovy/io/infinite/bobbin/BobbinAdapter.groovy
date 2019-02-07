package io.infinite.bobbin

import groovy.transform.CompileStatic
import org.slf4j.helpers.MarkerIgnoringBase

@CompileStatic
class BobbinAdapter extends MarkerIgnoringBase {

    String className
    Bobbin bobbin

    BobbinAdapter(String className, Bobbin bobbin) {
        this.className = className
        this.bobbin = bobbin
    }

    @Override
    boolean isTraceEnabled() {
        return this.bobbin.isTraceEnabled(className)
    }

    @Override
    void trace(String msg) {
        this.bobbin.trace(className, msg)
    }

    @Override
    void trace(String format, Object arg) {
        this.bobbin.trace(className, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        this.bobbin.trace(className, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        this.bobbin.trace(className, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        this.bobbin.trace(className, msg, t)
    }

    @Override
    boolean isDebugEnabled() {
        this.bobbin.isDebugEnabled(className)
    }

    @Override
    void debug(String msg) {
        this.bobbin.debug(className, msg)
    }

    @Override
    void debug(String format, Object arg) {
        this.bobbin.debug(className, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        this.bobbin.debug(className, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        this.bobbin.debug(className, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        this.bobbin.debug(className, msg, t)
    }

    @Override
    boolean isInfoEnabled() {
        this.bobbin.isInfoEnabled(className)
    }

    @Override
    void info(String msg) {
        this.bobbin.info(className, msg)
    }

    @Override
    void info(String format, Object arg) {
        this.bobbin.info(className, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        this.bobbin.info(className, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        this.bobbin.info(className, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        this.bobbin.info(className, msg, t)
    }

    @Override
    boolean isWarnEnabled() {
        this.bobbin.isWarnEnabled(className)
    }

    @Override
    void warn(String msg) {
        this.bobbin.warn(className, msg)
    }

    @Override
    void warn(String format, Object arg) {
        this.bobbin.warn(className, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        this.bobbin.warn(className, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        this.bobbin.warn(className, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        this.bobbin.warn(className, msg, t)
    }

    @Override
    boolean isErrorEnabled() {
        this.bobbin.isErrorEnabled(className)
    }

    @Override
    void error(String msg) {
        this.bobbin.error(className, msg)
    }

    @Override
    void error(String format, Object arg) {
        this.bobbin.error(className, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        this.bobbin.error(className, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        this.bobbin.error(className, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        this.bobbin.error(className, msg, t)
    }

}
