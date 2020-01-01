package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.infinite.bobbin.config.AbstractDestinationConfig
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.FileDestinationConfig
import io.infinite.bobbin.destinations.Destination
import io.infinite.supplies.conf.ResourceLookup
import org.slf4j.helpers.Util

class BobbinDestinationFactory {

    static List<Destination> destinations = initDestinations()

    static BobbinEngine createBobbinEngine(AbstractDestinationConfig destinationConfig) {
        return new GroovyClassLoader(destinationConfig.getClass().getClassLoader()).parseClass(getBobbinEngineImplCode(
                destinationConfig
        )).newInstance() as BobbinEngine
    }

    static synchronized List<Destination> initDestinations() {
        List<Destination> destinations = new ArrayList<>()
        BobbinConfig bobbinConfig
        String configResourceString = new ResourceLookup("Bobbin", "Bobbin.yml", true).getResourceAsString()
        if (configResourceString != null) {
            bobbinConfig = new ObjectMapper(new YAMLFactory()).readValue(
                    configResourceString
                    , BobbinConfig.class
            )
        } else {
            bobbinConfig = zeroConf()
        }
        bobbinConfig.destinations.each { destinationConfig ->
            Destination destination = destinationConfig.getDestinationClass().newInstance(
                    destinationConfig
            ) as Destination
            destination.bobbinEngine = createBobbinEngine(destinationConfig)
            destinations.add(destination)
        }
        return destinations
    }

    static BobbinConfig zeroConf() {
        Util.report("Bobbin: using zero configuration")
        return new BobbinConfig()
    }

    static String getBobbinEngineImplCode(AbstractDestinationConfig destinationConfig) {
        return """import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinEngine

@CompileStatic
class BobbinEngineImpl extends BobbinEngine {
    
    Boolean isFiltered(String level, String className) {
        $destinationConfig.filter
    }

    String evalFileName(String level, String className, String date) {
        ${destinationConfig instanceof FileDestinationConfig ? destinationConfig.fileName : "return ''"}
    }

    String formatLine(String level, String className, String date, String message) {
        $destinationConfig.format
    }

    String formatLineArg(String level, String className, String date, String message, Object arg) {
        $destinationConfig.formatArg
    }

    String formatLineArgs(String level, String className, String date, String message, Object... args) {
        $destinationConfig.formatArgs
    }

    String formatLineArg1Arg2(String level, String className, String date, String message, Object arg1, Object arg2) {
        $destinationConfig.formatArg1Arg2
    }

    String formatLineThrowable(String level, String className, String date, String message, Throwable throwable) {
        $destinationConfig.formatThrowable
    }

    String getDateFormat() {
        "$destinationConfig.dateFormat"
    }

    String getDateTimeFormat() {
        "$destinationConfig.dateTimeFormat"
    }

}"""
    }

}
