package io.infinite.bobbin

class BobbinConfig {

    static class Destination {
        String name
        Map<String, String> properties = [:]
        String format
        String levels
        String classes
        String dateFormat = "yyyy-MM-dd"
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
    }

    String levels
    String classes
    List<Destination> destinations = []

}
