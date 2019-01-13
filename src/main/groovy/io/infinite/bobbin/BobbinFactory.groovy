package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.supplies.ast.cache.Cache
import io.infinite.supplies.conf.ResourceLookupThread
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

class BobbinFactory implements ILoggerFactory {

    @Cache
    BobbinConfig bobbinConfig = initBobbinConfig()

    String getConfName() {
        return "Bobbin.json"
    }

    BobbinConfig initBobbinConfig() {
        BobbinConfig bobbinConfig
        File configResource = new ResourceLookupThread("Bobbin", getConfName(), true).getResourceAsFile()
        if (configResource != null) {
            bobbinConfig = new ObjectMapper().readValue(
                    configResource
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
        initBobbinIfNeeded()
        return new BobbinAdapter(name)
    }

    void initBobbinIfNeeded() {
        Bobbin bobbin = BobbinThreadLocal.get()
        if (bobbin == null) {
            bobbin = new Bobbin(getBobbinConfig())
            BobbinThreadLocal.set(bobbin)
        }
    }

}
