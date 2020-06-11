package io.infinite.bobbin.config

abstract class AbstractBobbinConfig {

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

    List<String> getLevels() { return levels }

    List<String> getPackages() { return packages }

    List<String> getClasses() { return classes }

    String getFilter() { return filter }

    String getDateFormat() { return dateFormat }

    String getDateTimeFormat() { return dateTimeFormat }

    String getDelimiter() { return delimiter }

    String getLineBreak() { return lineBreak }

    String getFormat() { return format }

    String getFormatMessage() { return formatMessage }

    String getFormatThrowable() { return formatThrowable }

    String getFormatArg() { return formatArg }

    String getFormatArgs() { return formatArgs }

    String getFormatArg1Arg2() { return formatArg1Arg2 }

    Boolean isLevelEnabled(String level) {
        return getLevels().isEmpty() || getLevels().contains(level)
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
