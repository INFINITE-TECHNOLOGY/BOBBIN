package io.infinite.bobbin

import io.infinite.speedometer.Speedometer

@Speedometer
class BobbinConfig {

    static class Destination {
        String name
        Map<String, String> properties = [:]
        String format
        String levels
        String classes
        String dateFormat = "yyyy-MM-dd"
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    }

    String levels = "false"
    String classes = "false"
    List<Destination> destinations = []

}
