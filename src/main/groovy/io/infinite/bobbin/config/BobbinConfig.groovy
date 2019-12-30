package io.infinite.bobbin.config

class BobbinConfig {

    List<AbstractDestinationConfig> destinations = [
            new ConsoleDestinationConfig(
                    levels: ["info", "warn", "error"],
                    formatThrowable: "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + throwable.message + '\\n'"
            ),
            new FileDestinationConfig(
                    levels: ["error"],
                    "fileName": "'./error.log'"
            )
    ]

}
