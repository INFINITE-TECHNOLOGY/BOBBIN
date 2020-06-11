package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonManagedReference

class BobbinConfig extends AbstractBobbinConfig {

    List<String> levels = []
    List<String> packages = []
    List<String> classes = []
    String filter = "none"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String delimiter = "|"
    String lineBreak = System.lineSeparator()
    String format = "dateTime + delimiter + level + delimiter + threadName + delimiter + className + delimiter + message"
    String formatMessage
    String formatThrowable
    String formatArg
    String formatArgs
    String formatArg1Arg2

    String getFormatMessage() {
        formatMessage = formatMessage ?: "$format"
    }

    String getFormatThrowable() {
        formatThrowable = formatThrowable ?: "$format + delimiter + exceptionUtils.stacktrace(throwable)"
    }

    String getFormatArg() {
        formatArg = formatArg ?: "$format + delimiter + arg.toString()"
    }

    String getFormatArgs() {
        formatArgs = formatArgs ?: "$format + delimiter + args.toString()"
    }

    String getFormatArg1Arg2() {
        formatArg1Arg2 = formatArg1Arg2 ?: "$format + delimiter + arg1.toString() + delimiter + arg2.toString()"
    }

    @JsonManagedReference
    List<AbstractDestinationConfig> destinations = [
            new ConsoleDestinationConfig(
                    parentConfig: this,
                    levels: ["info", "warn", "error"],
                    formatThrowable: "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + throwable.message + '\\n'"
            ),
            new FileDestinationConfig(
                    parentConfig: this,
                    levels: ["error"],
                    "fileName": "'./error.log'"
            )
    ]

}
