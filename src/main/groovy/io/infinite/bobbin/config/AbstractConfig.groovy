package io.infinite.bobbin.config

abstract class AbstractConfig {

    List<String> levels = new ArrayList<>()
    List<String> packages = new ArrayList<>()
    List<String> classes = new ArrayList<>()
    String filter
    String dateFormat
    String dateTimeFormat
    String format
    String formatThrowable
    String formatArg
    String formatArgs
    String formatArg1Arg2

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

}
