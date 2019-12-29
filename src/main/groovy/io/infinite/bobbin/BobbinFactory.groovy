package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.destinations.Destination
import io.infinite.supplies.ast.cache.CacheFieldInit
import io.infinite.supplies.conf.ResourceLookup
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

class BobbinFactory implements ILoggerFactory {

    String confName = "Bobbin.yml"

    @CacheFieldInit
    BobbinConfig bobbinConfig = initBobbinConfig()

    synchronized BobbinConfig initBobbinConfig() {
        BobbinConfig bobbinConfig
        String configResourceString = new ResourceLookup("Bobbin", confName, true).getResourceAsString()
        if (configResourceString != null) {
            bobbinConfig = new ObjectMapper(new YAMLFactory()).readValue(
                    configResourceString
                    , BobbinConfig.class
            )
        } else {
            bobbinConfig = zeroConf()
        }
        return bobbinConfig
    }

    BobbinConfig zeroConf() {
        Util.report("Bobbin: using zero configuration")
        return new BobbinConfig()
    }

    @Override
    Logger getLogger(String name) {
        Bobbin bobbin = new Bobbin(name)
        bobbinConfig.destinations.each {
            Destination destination = Class.forName(it.name).newInstance(
                    it
            ) as Destination
            if (it.bobbinScriptEngine != null) {
                destination.bobbinScriptEngine = it.bobbinScriptEngine
            } else {
                it.bobbinScriptEngine = new BobbinScriptEngineFactory().getDestinationBobbinScriptEngine(it)
                destination.bobbinScriptEngine = it.bobbinScriptEngine
            }
            bobbin.destinations.add(destination)
        }
        bobbin.bobbinScriptEngine = new BobbinScriptEngineFactory().bobbinScriptEngine
        return bobbin
    }

}
