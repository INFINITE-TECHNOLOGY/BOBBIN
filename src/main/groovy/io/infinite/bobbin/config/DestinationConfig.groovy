package io.infinite.bobbin.config

import io.infinite.bobbin.BobbinScriptEngine

class DestinationConfig extends AbstractConfig {

    String name = "io.infinite.bobbin.destinations.ConsoleDestination"
    BobbinConfig bobbinConfig
    Map<String, String> properties = [:]
    BobbinScriptEngine bobbinScriptEngine

    @Override
    List<String> getLevels() {
        return super.@levels ?: bobbinConfig.getLevels()
    }

    @Override
    List<String> getPackages() {
        return super.@packages ?: bobbinConfig.getPackages()
    }

    @Override
    List<String> getClasses() {
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