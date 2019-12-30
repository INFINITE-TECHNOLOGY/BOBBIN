package io.infinite.bobbin.config

class BobbinConfig extends AbstractConfig {

    String filter = "none"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String format = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '\\n'"
    String formatThrowable = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + exceptionUtils.stacktrace(throwable) + '\\n'"
    String formatArg = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + arg.toString() + '\\n'"
    String formatArgs = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + args.toString() + '\\n'"
    String formatArg1Arg2 = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + arg1.toString() + ';' + arg2.toString() + '\\n'"
    List<DestinationConfig> destinations = [
            new DestinationConfig(
                    levels: ["info", "warn", "error"],
                    formatThrowable: "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + throwable.message + '\\n'"
            ),
            new DestinationConfig(
                    name: "io.infinite.bobbin.destinations.FileDestination",
                    levels: ["error"],
                    properties: ["fileName": "'./error.log'"]
            )
    ]

    void setDestinations(List<DestinationConfig> destinations) {
        this.destinations = destinations
        setBobbinConfig()
    }

    void setBobbinConfig() {
        destinations.each {it.setBobbinConfig(this)}
    }

    {
        setBobbinConfig()
    }

}
