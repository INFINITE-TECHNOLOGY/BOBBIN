package io.infinite.bobbin.config

class BobbinConfig extends AbstractConfig {

    String levels = "true"
    String classes = "true"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String format = "\"\${dateTime}|\${level}|\${threadName}|\${className}|\${event.message}\\n\""
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