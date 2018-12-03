package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

class BobbinFactory implements ILoggerFactory {

    static ThreadLocal bobbinThreadLocal = new ThreadLocal()

    @Override
    Logger getLogger(String name) {
        Bobbin bobbin = bobbinThreadLocal.get() as Bobbin
        if (bobbin == null) {
            if (new File("./Bobbin.json").exists()) {
                BobbinConfig bobbinConfig = new ObjectMapper().readValue(new File("./Bobbin.json").getText(), BobbinConfig.class)
                bobbin = new Bobbin(bobbinConfig)
                bobbinThreadLocal.set(bobbin)
                return new BobbinNameAdapter(bobbin, name)
            } else {
                Util.report("Missing Bobbin.json.")
                return new BobbinNameAdapter(new Bobbin(), name)
            }
        } else {
            return new BobbinNameAdapter(bobbin, name)
        }
    }
}
