package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

class TestBobbinFactory {

    String bobbinConfFileName

    TestBobbinFactory(String bobbinConfFileName) {
        this.bobbinConfFileName = bobbinConfFileName
    }

    Logger getLogger(String name) {
        initBobbinIfNeeded()
        return new BobbinNameAdapter(name)
    }

    void initBobbinIfNeeded() {
        Bobbin bobbin = BobbinFactory.bobbinThreadLocal.get() as Bobbin
        if (bobbin == null) {
            URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() {
                URL run() {
                    ClassLoader threadCL = Thread.currentThread().getContextClassLoader()
                    if (threadCL != null) {
                        return threadCL.getResource(bobbinConfFileName)
                    } else {
                        return ClassLoader.getSystemResource(bobbinConfFileName)
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
                BobbinFactory.bobbinThreadLocal.set(bobbin)
            } else {
                Util.report("Missing Bobbin.json at classpath")
                BobbinFactory.bobbinThreadLocal.set(new Bobbin(new BobbinConfig()))
            }
        }
    }

    Bobbin getBobbin() {
        initBobbinIfNeeded()
        return BobbinFactory.bobbinThreadLocal.get() as Bobbin
    }

}
