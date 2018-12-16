package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

class BobbinFactory implements ILoggerFactory {

    static ThreadLocal bobbinThreadLocal = new ThreadLocal()

    ThreadLocal getBobbinThreadLocal() {
        return bobbinThreadLocal
    }

    @Override
    Logger getLogger(String name) {
        initBobbinIfNeeded()
        return new BobbinNameAdapter(name)
    }

    void initBobbinIfNeeded() {
        Bobbin bobbin = getBobbinThreadLocal().get() as Bobbin
        if (bobbin == null) {
            URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() {
                URL run() {
                    ClassLoader threadCL = Thread.currentThread().getContextClassLoader()
                    if (threadCL != null) {
                        return threadCL.getResource("Bobbin.json")
                    } else {
                        return ClassLoader.getSystemResource("Bobbin.json")
                    }
                }
            })
            File file
            if (url != null) {
                file = new File(url.toURI())
            } else {
                file = new File("./Bobbin.json")
            }
            if (file.exists()) {
                BobbinConfig bobbinConfig = new ObjectMapper().readValue(file.getText(), BobbinConfig.class)
                bobbin = new Bobbin(bobbinConfig)
                getBobbinThreadLocal().set(bobbin)
            } else {
                Util.report("Missing Bobbin.json at classpath")
                getBobbinThreadLocal().set(new Bobbin(new BobbinConfig()))
            }
        }
    }

    Bobbin getBobbin() {
        initBobbinIfNeeded()
        return getBobbinThreadLocal().get() as Bobbin
    }

}
