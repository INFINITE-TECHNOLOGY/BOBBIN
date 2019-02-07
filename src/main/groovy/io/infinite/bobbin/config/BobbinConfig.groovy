package io.infinite.bobbin.config

import groovy.transform.CompileStatic

@CompileStatic
class BobbinConfig extends AbstractConfig {

    String levels = "true"
    String classes = "true"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String format = "\"\${dateTime}|\${level}|\${threadName}|\${className}|\${event.message}\${event.error}\\n\""
    String errorFormat = "\"|\${event.throwable.message}\\n\${event.stacktrace}\""
    List<DestinationConfig> destinations = [new DestinationConfig()]

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
