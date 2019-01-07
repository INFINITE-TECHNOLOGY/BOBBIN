package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.supplies.ast.cache.Static
import io.infinite.supplies.conf.ResourceLookupThread
import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class BobbinFactory implements ILoggerFactory {

    ThreadLocal bobbinThreadLocal = new ThreadLocal()

    @Static
    final BobbinConfig bobbinConfig = initBobbinConfig()

    BobbinConfig getBobbinConfig() {
        return bobbinConfig
    }

    String getConfName() {
        return "Bobbin.json"
    }

    BobbinConfig initBobbinConfig() {
        BobbinConfig bobbinConfig = new ObjectMapper().readValue(
                new ResourceLookupThread("Bobbin", getConfName(), true).getResourceAsFile()
                , BobbinConfig.class
        )
        if (bobbinConfig == null) {
            bobbinConfig = new BobbinConfig() //todo: noconf here
        }
        return bobbinConfig
    }

    @Override
    Logger getLogger(String name) {
        initBobbinIfNeeded()
        return new BobbinAdapter(name)
    }

    void initBobbinIfNeeded() {
        Bobbin bobbin = getBobbinThreadLocal().get() as Bobbin
        if (bobbin == null) {
            bobbin = new Bobbin(getBobbinConfig())
            getBobbinThreadLocal().set(bobbin)
        }
    }

    Bobbin getBobbin() {
        initBobbinIfNeeded()
        return getBobbinThreadLocal().get() as Bobbin
    }

}
