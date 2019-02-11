package io.infinite.bobbin.config

import groovy.transform.CompileStatic

@CompileStatic
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
    String getFormatThrowable() {
        return super.@formatThrowable ?: bobbinConfig.getFormatThrowable()
    }

    @Override
    String getFormatArg() {
        return super.@formatArg ?: bobbinConfig.getFormatArg()
    }

    @Override
    String getFormatArgs() {
        return super.@formatArgs ?: bobbinConfig.getFormatArgs()
    }

    @Override
    String getFormatArg1Arg2() {
        return super.@formatArg1Arg2 ?: bobbinConfig.getFormatArg1Arg2()
    }

}