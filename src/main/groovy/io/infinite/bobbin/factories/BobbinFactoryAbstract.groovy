package io.infinite.bobbin.factories

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinAdapter
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.ThreadLocal
import org.slf4j.ILoggerFactory
import org.slf4j.Logger

abstract class BobbinFactoryAbstract implements ILoggerFactory {

    ThreadLocal bobbinThreadLocal = new ThreadLocal()

    abstract BobbinConfig getBobbinConf()

    @Override
    Logger getLogger(String name) {
        initBobbinIfNeeded()
        return new BobbinAdapter(name)
    }

    void initBobbinIfNeeded() {
        Bobbin bobbin = getBobbinThreadLocal().get() as Bobbin
        if (bobbin == null) {
            bobbin = new Bobbin(getBobbinConf())
            getBobbinThreadLocal().set(bobbin)
        }
    }

    Bobbin getBobbin() {
        initBobbinIfNeeded()
        return getBobbinThreadLocal().get() as Bobbin
    }

}
