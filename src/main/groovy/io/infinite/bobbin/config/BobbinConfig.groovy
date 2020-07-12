package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonManagedReference

class BobbinConfig extends AbstractBobbinConfig {

    String filter = "none"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String delimiter = "|"
    String lineBreak = System.lineSeparator()
    String format = "dateTime + delimiter + level + delimiter + threadName + delimiter + className + delimiter + message"
    String formatMessage = "%format%"
    String formatThrowable = "%format% + delimiter + exceptionUtils.stacktrace(throwable)"
    String formatArg = "%format% + delimiter + arg.toString()"
    String formatArgs = "%format% + delimiter + args.toString()"
    String formatArg1Arg2 = "%format% + delimiter + arg1.toString() + delimiter + arg2.toString()"

    @JsonManagedReference
    List<AbstractDestinationConfig> destinations = [
            new ConsoleDestinationConfig(
                    parentConfig: this,
                    levels: ["info", "warn", "error"],
                    formatThrowable: "%format% + delimiter + 'see error.log for stack trace'"
            ),
            new FileDestinationConfig(
                    parentConfig: this,
                    levels: ["error"],
                    "fileName": "'./error.log'"
            )
    ]

}
