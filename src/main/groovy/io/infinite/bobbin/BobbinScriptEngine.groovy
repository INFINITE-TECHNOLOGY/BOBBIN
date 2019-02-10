package io.infinite.bobbin

import groovy.transform.CompileStatic

@CompileStatic
abstract class BobbinScriptEngine {

    final Boolean all = true

    final Boolean none = false

    String getThreadName() {
        return Thread.currentThread().getName()
    }

    String getThreadGroupName() {
        return Thread.currentThread().getThreadGroup().getName()
    }

    Class<org.slf4j.MDC> getMDC() {//keep full qualified reference for MDC getter shortcut
        return org.slf4j.MDC
    }

    abstract Boolean isLevelEnabled(String level)

    abstract Boolean isClassEnabled(String className)

    abstract String formatMessage(Event event)

    abstract String evalFileName()

}