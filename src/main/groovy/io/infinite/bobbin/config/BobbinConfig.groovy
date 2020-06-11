package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonManagedReference

class BobbinConfig extends AbstractBobbinConfig {

    @JsonManagedReference
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
