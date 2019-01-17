package io.infinite.bobbin.config

class DestinationConfig extends AbstractConfig {

    String name = "io.infinite.bobbin.destinations.ConsoleDestination"
    BobbinConfig bobbinConfig
    Map<String, String> properties = [:]

    @Override
    String getLevels() {
        return super.@levels ?: bobbinConfig.getLevels()
    }

    @Override
    String getClasses() {
        return super.@classes ?: bobbinConfig.getClasses()
    }

    @Override
    String getFilter() {
        return super.@filter ?: bobbinConfig.getFilter()
    }

    @Override
    String getDateFormat() {
        return super.@dateFormat ?: bobbinConfig.getDateFormat()
    }

    @Override
    String getDateTimeFormat() {
        return super.@dateTimeFormat ?: bobbinConfig.getDateTimeFormat()
    }

    @Override
    String getFormat() {
        return super.@format ?: bobbinConfig.getFormat()
    }

    @Override
    String getErrorFormat() {
        return super.@errorFormat ?: bobbinConfig.getErrorFormat()
    }

}