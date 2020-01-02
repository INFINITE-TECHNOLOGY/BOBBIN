package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="name")
abstract class AbstractDestinationConfig {

    List<String> levels = new ArrayList<>()
    List<String> packages = new ArrayList<>()
    List<String> classes = new ArrayList<>()
    String filter = "none"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String format = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '\\n'"
    String formatThrowable = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + exceptionUtils.stacktrace(throwable) + '\\n'"
    String formatArg = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + arg.toString() + '\\n'"
    String formatArgs = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + args.toString() + '\\n'"
    String formatArg1Arg2 = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + arg1.toString() + ';' + arg2.toString() + '\\n'"

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
