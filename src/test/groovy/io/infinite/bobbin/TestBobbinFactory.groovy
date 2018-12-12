package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger

class TestBobbinFactory {

    static ThreadLocal bobbinThreadLocal = new ThreadLocal()

    Logger getLogger(String name, String bobbinConfigString) {
        initBobbinIfNeeded(bobbinConfigString)
        return new BobbinNameAdapter(name)
    }

    static initBobbinIfNeeded(String bobbingConfigString) {
        Bobbin bobbin = bobbinThreadLocal.get() as Bobbin
        if (bobbin == null) {
            BobbinConfig bobbinConfig = new ObjectMapper().readValue(bobbingConfigString, BobbinConfig.class)
            bobbin = new Bobbin(bobbinConfig)
            bobbinThreadLocal.set(bobbin)
        }
    }

    static Bobbin getBobbin(String bobbingConfigString) {
        initBobbinIfNeeded(bobbingConfigString)
        return bobbinThreadLocal.get() as Bobbin
    }

}
