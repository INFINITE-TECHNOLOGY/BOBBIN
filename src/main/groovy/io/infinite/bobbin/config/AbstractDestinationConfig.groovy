package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "name")
abstract class AbstractDestinationConfig {

    List<String> levels = new ArrayList<>()
    List<String> packages = new ArrayList<>()
    List<String> classes = new ArrayList<>()
    String filter = "none"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String delimiter = "|"
    String lineBreak = "\\n"
    String format = "dateTime + delimiter + level + delimiter + threadName + delimiter + className + delimiter + message"
    String formatMessage = "$format + lineBreak"
    String formatThrowable = "$format + delimiter + exceptionUtils.stacktrace(throwable) + lineBreak"
    String formatArg = "$format + delimiter + arg.toString() + lineBreak"
    String formatArgs = "$format + delimiter + args.toString() + lineBreak"
    String formatArg1Arg2 = "$format + delimiter + arg1.toString() + delimiter + arg2.toString() + lineBreak"

    Boolean isLevelEnabled(String level) {
        return levels.isEmpty() || levels.contains(level)
    }

    Boolean isPackageEnabled(String className) {
        if (packages.isEmpty()) {
            return true
        }
        for (packageName in packages) {
            if (className.startsWith(packageName)) {
                return true
            }
        }
        return false
    }

    Boolean isClassEnabled(String className) {
        return classes.isEmpty() || classes.contains(className)
    }

    abstract Class getDestinationClass()

}
