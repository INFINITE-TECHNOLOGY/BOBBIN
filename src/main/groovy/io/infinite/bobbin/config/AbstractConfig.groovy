package io.infinite.bobbin.config

import groovy.transform.CompileStatic

@CompileStatic
abstract class AbstractConfig {

    String levels
    String classes
    String dateFormat
    String dateTimeFormat
    String format
    String formatThrowable
    String formatArg
    String formatArgs
    String formatArg1Arg2

}
