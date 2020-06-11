package io.infinite.bobbin.config

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
        return getLevels().empty || getLevels().contains(level)
    }

    Boolean isPackageEnabled(String className) {
        if (getPackages().empty) {
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
        return getClasses().isEmpty() || getClasses().contains(className)
    }

}
