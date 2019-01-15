package io.infinite.bobbin


import org.slf4j.helpers.MarkerIgnoringBase

class BobbinAdapter extends MarkerIgnoringBase {

    String className

    BobbinAdapter(String className) {
        this.className = className
    }

    @Override
    boolean isTraceEnabled() {
        return BobbinThreadLocal.getBobbin().isTraceEnabled(className)
    }

    @Override
    void trace(String msg) {
        BobbinThreadLocal.getBobbin().trace(className, msg)
    }

    @Override
    void trace(String format, Object arg) {
        BobbinThreadLocal.getBobbin().trace(className, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.getBobbin().trace(className, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        BobbinThreadLocal.getBobbin().trace(className, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        BobbinThreadLocal.getBobbin().trace(className, msg, t)
    }

    @Override
    boolean isDebugEnabled() {
        BobbinThreadLocal.getBobbin().isDebugEnabled(className)
    }

    @Override
    void debug(String msg) {
        BobbinThreadLocal.getBobbin().debug(className, msg)
    }

    @Override
    void debug(String format, Object arg) {
        BobbinThreadLocal.getBobbin().debug(className, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.getBobbin().debug(className, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        BobbinThreadLocal.getBobbin().debug(className, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        BobbinThreadLocal.getBobbin().debug(className, msg, t)
    }

    @Override
    boolean isInfoEnabled() {
        BobbinThreadLocal.getBobbin().isInfoEnabled(className)
    }

    @Override
    void info(String msg) {
        BobbinThreadLocal.getBobbin().info(className, msg)
    }

    @Override
    void info(String format, Object arg) {
        BobbinThreadLocal.getBobbin().info(className, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.getBobbin().info(className, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        BobbinThreadLocal.getBobbin().info(className, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        BobbinThreadLocal.getBobbin().info(className, msg, t)
    }

    @Override
    boolean isWarnEnabled() {
        BobbinThreadLocal.getBobbin().isWarnEnabled(className)
    }

    @Override
    void warn(String msg) {
        BobbinThreadLocal.getBobbin().warn(className, msg)
    }

    @Override
    void warn(String format, Object arg) {
        BobbinThreadLocal.getBobbin().warn(className, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        BobbinThreadLocal.getBobbin().warn(className, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.getBobbin().warn(className, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        BobbinThreadLocal.getBobbin().warn(className, msg, t)
    }

    @Override
    boolean isErrorEnabled() {
        BobbinThreadLocal.getBobbin().isErrorEnabled(className)
    }

    @Override
    void error(String msg) {
        BobbinThreadLocal.getBobbin().error(className, msg)
    }

    @Override
    void error(String format, Object arg) {
        BobbinThreadLocal.getBobbin().error(className, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.getBobbin().error(className, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        BobbinThreadLocal.getBobbin().error(className, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        BobbinThreadLocal.getBobbin().error(className, msg, t)
    }

}
