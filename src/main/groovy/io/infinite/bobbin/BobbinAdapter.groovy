package io.infinite.bobbin


import org.slf4j.helpers.MarkerIgnoringBase

class BobbinAdapter extends MarkerIgnoringBase {

    String className

    BobbinAdapter(String className) {
        this.className = className
    }

    @Override
    boolean isTraceEnabled() {
        return BobbinThreadLocal.get().isTraceEnabled(className)
    }

    @Override
    void trace(String msg) {
        BobbinThreadLocal.get().trace(className, msg)
    }

    @Override
    void trace(String format, Object arg) {
        BobbinThreadLocal.get().trace(className, format, arg)
    }

    @Override
    void trace(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.get().trace(className, format, arg1, arg2)
    }

    @Override
    void trace(String format, Object... arguments) {
        BobbinThreadLocal.get().trace(className, format, arguments)
    }

    @Override
    void trace(String msg, Throwable t) {
        BobbinThreadLocal.get().trace(className, msg, t)
    }

    @Override
    boolean isDebugEnabled() {
        BobbinThreadLocal.get().isDebugEnabled(className)
    }

    @Override
    void debug(String msg) {
        BobbinThreadLocal.get().debug(className, msg)
    }

    @Override
    void debug(String format, Object arg) {
        BobbinThreadLocal.get().debug(className, format, arg)
    }

    @Override
    void debug(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.get().debug(className, format, arg1, arg2)
    }

    @Override
    void debug(String format, Object... arguments) {
        BobbinThreadLocal.get().debug(className, format, arguments)
    }

    @Override
    void debug(String msg, Throwable t) {
        BobbinThreadLocal.get().debug(className, msg, t)
    }

    @Override
    boolean isInfoEnabled() {
        BobbinThreadLocal.get().isInfoEnabled(className)
    }

    @Override
    void info(String msg) {
        BobbinThreadLocal.get().info(className, msg)
    }

    @Override
    void info(String format, Object arg) {
        BobbinThreadLocal.get().info(className, format, arg)
    }

    @Override
    void info(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.get().info(className, format, arg1, arg2)
    }

    @Override
    void info(String format, Object... arguments) {
        BobbinThreadLocal.get().info(className, format, arguments)
    }

    @Override
    void info(String msg, Throwable t) {
        BobbinThreadLocal.get().info(className, msg, t)
    }

    @Override
    boolean isWarnEnabled() {
        BobbinThreadLocal.get().isWarnEnabled(className)
    }

    @Override
    void warn(String msg) {
        BobbinThreadLocal.get().warn(className, msg)
    }

    @Override
    void warn(String format, Object arg) {
        BobbinThreadLocal.get().warn(className, format, arg)
    }

    @Override
    void warn(String format, Object... arguments) {
        BobbinThreadLocal.get().warn(className, format, arguments)
    }

    @Override
    void warn(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.get().warn(className, format, arg1, arg2)
    }

    @Override
    void warn(String msg, Throwable t) {
        BobbinThreadLocal.get().warn(className, msg, t)
    }

    @Override
    boolean isErrorEnabled() {
        BobbinThreadLocal.get().isErrorEnabled(className)
    }

    @Override
    void error(String msg) {
        BobbinThreadLocal.get().error(className, msg)
    }

    @Override
    void error(String format, Object arg) {
        BobbinThreadLocal.get().error(className, format, arg)
    }

    @Override
    void error(String format, Object arg1, Object arg2) {
        BobbinThreadLocal.get().error(className, format, arg1, arg2)
    }

    @Override
    void error(String format, Object... arguments) {
        BobbinThreadLocal.get().error(className, format, arguments)
    }

    @Override
    void error(String msg, Throwable t) {
        BobbinThreadLocal.get().error(className, msg, t)
    }

}
