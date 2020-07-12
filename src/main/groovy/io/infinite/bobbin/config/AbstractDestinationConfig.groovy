package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "name")
abstract class AbstractDestinationConfig extends AbstractBobbinConfig {

    @JsonBackReference
    BobbinConfig parentConfig

    abstract Class getDestinationClass()

    @Override
    String getDateFormat() {
        return super.dateFormat ?: parentConfig.getDateFormat()
    }

    @Override
    String getDateTimeFormat() {
        return super.dateTimeFormat ?: parentConfig.getDateTimeFormat()
    }

    @Override
    String getFormat() {
        return super.format ?: parentConfig.getFormat()
    }

    @Override
    String getFormatMessage() {
        return super.formatMessage ?: parentConfig.getFormatMessage()
    }


    @Override
    String getFormatThrowable() {
        return super.formatThrowable ?: parentConfig.getFormatThrowable()
    }

    @Override
    String getFormatArg() {
        return super.formatArg ?: parentConfig.getFormatArg()
    }

    @Override
    String getFormatArgs() {
        return super.formatArgs ?: parentConfig.getFormatArgs()
    }

    @Override
    String getFormatArg1Arg2() {
        return super.formatArg1Arg2 ?: parentConfig.getFormatArg1Arg2()
    }

    @Override
    String getFilter() {
        return super.filter ?: parentConfig.getFilter()
    }

    @Override
    String getDelimiter() {
        return super.delimiter ?: parentConfig.getDelimiter()
    }

    @Override
    String getLineBreak() {
        return super.lineBreak ?: parentConfig.getLineBreak()
    }

}