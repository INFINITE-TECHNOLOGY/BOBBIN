package io.infinite.bobbin

import org.slf4j.helpers.MarkerIgnoringBase

class BobbinNameAdapter extends MarkerIgnoringBase {

    BobbinFactory bobbinFactory

    String className

    Bobbin bobbin() {
        return bobbinFactory.getBobbin()
    }

    BobbinNameAdapter(String className, BobbinFactory bobbinFactory) {
        this.className = className
        this.bobbinFactory = bobbinFactory
    }

    @Override
    boolean isTraceEnabled() {
        return bobbin().isTraceEnabled(className)
    }

    @Override
    void trace(String msg) {
        bobbin().trace(className, msg)
    }

    @Override
    void trace(String format, Object arg) {
        bobbin().trace(className, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        bobbin().trace(className, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        bobbin().trace(className, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        bobbin().trace(className, msg, t)
    }

    @Override
    boolean isDebugEnabled() {
        bobbin().isDebugEnabled(className)
    }

    @Override
    void debug(String msg) {
        bobbin().debug(className, msg)
    }

    @Override
    void debug(String format, Object arg) {
        bobbin().debug(className, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        bobbin().debug(className, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        bobbin().debug(className, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        bobbin().debug(className, msg, t)
    }

    @Override
    boolean isInfoEnabled() {
        bobbin().isInfoEnabled(className)
    }

    @Override
    void info(String msg) {
        bobbin().info(className, msg)
    }

    @Override
    void info(String format, Object arg) {
        bobbin().info(className, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        bobbin().info(className, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        bobbin().info(className, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        bobbin().info(className, msg, t)
    }

    @Override
    boolean isWarnEnabled() {
        bobbin().isWarnEnabled(className)
    }

    @Override
    void warn(String msg) {
        bobbin().warn(className, msg)
    }

    @Override
    void warn(String format, Object arg) {
        bobbin().warn(className, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        bobbin().warn(className, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        bobbin().warn(className, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        bobbin().warn(className, msg, t)
    }

    @Override
    boolean isErrorEnabled() {
        bobbin().isErrorEnabled(className)
    }

    @Override
    void error(String msg) {
        bobbin().error(className, msg)
    }

    @Override
    void error(String format, Object arg) {
        bobbin().error(className, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        bobbin().error(className, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        bobbin().error(className, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        bobbin().error(className, msg, t)
    }
}
