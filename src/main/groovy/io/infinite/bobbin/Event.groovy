package io.infinite.bobbin

import groovy.transform.CompileStatic
import io.infinite.supplies.ast.exceptions.ExceptionUtils

@CompileStatic
class Event {
    Level level
    Date date = new Date()
    String message
    String className
    String formattedMessage
    def arguments
    Throwable throwable
    String error

    String getStacktrace() {
        throwable != null ? new ExceptionUtils().stacktrace(throwable) : ""
    }

    String getSanitizedStacktrace() {
        throwable != null ? new ExceptionUtils().sanitizedStacktrace(throwable) : ""
    }

}