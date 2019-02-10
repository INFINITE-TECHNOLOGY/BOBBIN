package io.infinite.bobbin

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import groovy.transform.CompileStatic
import groovy.transform.Memoized
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.supplies.ast.cache.Cache

@CompileStatic
class BobbinScriptEngineFactory {

    @Cache
    SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()

    @Cache
    File combinedTemplateFile = new File(this.getClass().getResource(combinedTemplateFileName).toURI())

    @Cache
    Template combinedTemplate = simpleTemplateEngine.createTemplate(combinedTemplateFile)

    @Cache
    GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader())

    @Cache
    BobbinConfig bobbinConfig = new BobbinFactory().bobbinConfig

    @Cache
    String bobbinScriptEngineImplCode = combinedTemplate.make([
            "levelScript"  : bobbinConfig.levels,
            "classesScript": bobbinConfig.classes,
            "formatScript" : bobbinConfig.format

    ])

    @Cache
    Class bobbinScriptEngineImplClass = groovyClassLoader.parseClass(bobbinScriptEngineImplCode)

    @Cache
    BobbinScriptEngine bobbinScriptEngine = bobbinScriptEngineImplClass.newInstance() as BobbinScriptEngine

    String getCombinedTemplateFileName() {
        return "CombinedTemplate.groovy"
    }

    @Memoized
    BobbinScriptEngine getDestinationBobbinScriptEngine(DestinationConfig destinationConfig) {
        return groovyClassLoader.parseClass(combinedTemplate.make([
                "levelScript"  : destinationConfig.levels ?: bobbinConfig.levels,
                "classesScript": destinationConfig.classes ?:bobbinConfig.classes,
                "formatScript" : destinationConfig.format ?: bobbinConfig.format

        ]) as String).newInstance() as BobbinScriptEngine
    }

}
