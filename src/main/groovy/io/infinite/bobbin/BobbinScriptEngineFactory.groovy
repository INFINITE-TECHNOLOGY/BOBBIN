package io.infinite.bobbin

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import groovy.transform.CompileStatic
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.supplies.ast.cache.Cache

@CompileStatic
class BobbinScriptEngineFactory {

    @Cache
    SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()

    @Cache
    String combinedTemplateFileString = getTemplateText()

    @Cache
    Template combinedTemplate = simpleTemplateEngine.createTemplate(combinedTemplateFileString)

    @Cache
    GroovyClassLoader groovyClassLoader = new GroovyClassLoader(getClass().getClassLoader())

    BobbinConfig bobbinConfig = new BobbinFactory().bobbinConfig

    @Cache
    String bobbinScriptEngineImplCode = combinedTemplate.make([
            "levelScript"  : bobbinConfig.levels,
            "classesScript": bobbinConfig.classes,
            "formatScript" : bobbinConfig.format,
            "argFormatScript" : bobbinConfig.formatArg,
            "argsFormatScript" : bobbinConfig.formatArgs,
            "arg1arg2FormatScript" : bobbinConfig.formatArg1Arg2,
            "throwableFormatScript" : bobbinConfig.formatThrowable,
            "dateFormat": bobbinConfig.dateFormat,
            "dateTimeFormat": bobbinConfig.dateTimeFormat,
            "fileNameScript" : "\"\""
    ])

    @Cache
    Class bobbinScriptEngineImplClass = groovyClassLoader.parseClass(bobbinScriptEngineImplCode)

    @Cache
    BobbinScriptEngine bobbinScriptEngine = bobbinScriptEngineImplClass.newInstance(bobbinScriptEngineImplCode) as BobbinScriptEngine

    BobbinScriptEngine getDestinationBobbinScriptEngine(DestinationConfig destinationConfig) {
        return groovyClassLoader.parseClass(combinedTemplate.make([
                "levelScript"  : destinationConfig.levels ?: bobbinConfig.levels,
                "classesScript": destinationConfig.classes ?: bobbinConfig.classes,
                "formatScript" : destinationConfig.format ?: bobbinConfig.format,
                "argFormatScript" : destinationConfig.formatArg ?: bobbinConfig.formatArg,
                "argsFormatScript" : destinationConfig.formatArgs ?: bobbinConfig.formatArgs,
                "arg1arg2FormatScript" : destinationConfig.formatArg1Arg2 ?: bobbinConfig.formatArg1Arg2,
                "throwableFormatScript" : destinationConfig.formatThrowable ?: bobbinConfig.formatThrowable,
                "dateFormat": destinationConfig.dateFormat ?: bobbinConfig.dateFormat,
                "dateTimeFormat": destinationConfig.dateTimeFormat ?: bobbinConfig.dateTimeFormat,
                "fileNameScript" : destinationConfig.properties.get("fileName")

        ]) as String).newInstance(bobbinScriptEngineImplCode) as BobbinScriptEngine
    }

    String getTemplateText() {
        return """import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinScriptEngine

@CompileStatic
class BobbinScriptEngineImpl extends BobbinScriptEngine {

    BobbinScriptEngineImpl(String code) {
        super(code)
    }

    Boolean isLevelEnabled(String level) {
        \$levelScript
    }

    Boolean isClassEnabled(String className) {
        \$classesScript
    }

    String evalFileName(String level, String className, String date) {
        \$fileNameScript
    }

    String formatLine(String level, String className, String date, String message) {
        \$formatScript
    }

    String formatLineArg(String level, String className, String date, String message, Object arg) {
        \$argFormatScript
    }

    String formatLineArgs(String level, String className, String date, String message, Object... args) {
        \$argsFormatScript
    }

    String formatLineArg1Arg2(String level, String className, String date, String message, Object arg1, Object arg2) {
        \$arg1arg2FormatScript
    }

    String formatLineThrowable(String level, String className, String date, String message, Throwable throwable) {
        \$throwableFormatScript
    }

    String getDateFormat() {
        "\$dateFormat"
    }

    String getDateTimeFormat() {
        "\$dateTimeFormat"
    }

}"""
    }

}
