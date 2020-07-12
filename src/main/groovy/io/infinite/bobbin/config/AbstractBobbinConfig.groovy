package io.infinite.bobbin.config

import io.infinite.bobbin.Level

abstract class AbstractBobbinConfig {

    List<String> levels
    List<String> packages
    List<String> classes
    String filter
    String dateFormat
    String dateTimeFormat
    String delimiter
    String lineBreak
    String format
    String formatMessage
    String formatThrowable
    String formatArg
    String formatArgs
    String formatArg1Arg2

    Boolean isLevelEnabled(String level) {
        return getLevels() == null || getLevels().contains(level)
    }

    Boolean isPackageEnabled(String className) {
        if (getPackages() == null) {
            return true
        }
        for (packageName in getPackages()) {
            if (className.startsWith(packageName)) {
                return true
            }
        }
        return false
    }

    Boolean isClassEnabled(String className) {
        return getClasses() == null || getClasses().contains(className)
    }

    Boolean needsLogging(String loggerName, Level level) {
        return (isLevelEnabled(level.value())
                && isPackageEnabled(loggerName)
                && isClassEnabled(loggerName))
    }

}
