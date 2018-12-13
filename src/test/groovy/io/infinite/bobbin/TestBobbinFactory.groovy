package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

class TestBobbinFactory {

    static ThreadLocal bobbinThreadLocal = new ThreadLocal()

    Logger getLogger(String name, String bobbinConfigFileName) {
        initBobbinIfNeeded(bobbinConfigFileName)
        return new BobbinNameAdapter(name)
    }

    static initBobbinIfNeeded(String bobbinConfigFileName) {
        Bobbin bobbin = bobbinThreadLocal.get() as Bobbin
        if (bobbin == null) {
            URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() {
                URL run() {
                    ClassLoader threadCL = Thread.currentThread().getContextClassLoader()
                    if (threadCL != null) {
                        return threadCL.getResource(bobbinConfigFileName)
                    } else {
                        return ClassLoader.getSystemResource(bobbinConfigFileName)
                    }
                }
            })
            File file
            if (url != null) {
                file = new File(url.toURI())
            }
            if (file?.exists()) {
                BobbinConfig bobbinConfig = new ObjectMapper().readValue(file.getText(), BobbinConfig.class)
                bobbin = new Bobbin(bobbinConfig)
                bobbinThreadLocal.set(bobbin)
            } else {
                Util.report("Missing $bobbinConfigFileName at classpath")
                bobbinThreadLocal.set(new Bobbin(new BobbinConfig()))
            }
        }
    }

    static Bobbin getBobbin(String bobbinConfigFileName) {
        initBobbinIfNeeded(bobbinConfigFileName)
        return bobbinThreadLocal.get() as Bobbin
    }

}
