package io.infinite.bobbin.factories


import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinAdapter
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.ThreadLocal
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

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

    void report(String msg) {
        Util.report("Bobbin: " + Thread.currentThread().getName().padRight(50) + msg)
    }

}
