package io.infinite.bobbin.config

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "name")
abstract class AbstractDestinationConfig extends AbstractBobbinConfig {

    @JsonBackReference
    BobbinConfig parentConfig

    List<String> levels
    List<String> packages
    List<String> classes
    String filter
    String dateFormat
    String dateTimeFormat
    String delimiter
    String lineBreak
    String format
    String formatMessage
    String formatThrowable
    String formatArg
    String formatArgs
    String formatArg1Arg2

    abstract Class getDestinationClass()

    @Override
    List<String> getLevels() {
        return levels ?: parentConfig.levels
    }

    List<String> getPackages() {
        return packages ?: parentConfig.packages
    }

    List<String> getClasses() {
        return classes ?: parentConfig.classes
    }

    String getFilter() {
        return filter ?: parentConfig.filter
    }

    String getDateFormat() {
        return dateFormat ?: parentConfig.dateFormat
    }

    String getDateTimeFormat() {
        return dateTimeFormat ?: parentConfig.dateTimeFormat
    }

    String getDelimiter() {
        return delimiter ?: parentConfig.delimiter
    }

    String getLineBreak() {
        return lineBreak ?: parentConfig.lineBreak
    }

    String getFormat() {
        return format ?: parentConfig.format
    }

    String getFormatMessage() {
        return formatMessage ?: parentConfig.formatMessage
    }

    String getFormatThrowable() {
        return formatThrowable ?: parentConfig.formatThrowable
    }

    String getFormatArg() {
        return formatArg ?: parentConfig.formatArg
    }

    String getFormatArgs() {
        return formatArgs ?: parentConfig.formatArgs
    }

    String getFormatArg1Arg2() {
        return formatArg1Arg2 ?: parentConfig.formatArg1Arg2
    }

}