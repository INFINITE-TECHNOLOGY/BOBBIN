package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

class BobbinFactory implements ILoggerFactory {

    static InheritableThreadLocal<Bobbin> bobbinThreadLocal = new InheritableThreadLocal()

    @Override
    Logger getLogger(String name) {
        Bobbin bobbin = bobbinThreadLocal.get() as Bobbin
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
            if (url != null && new File(url.toURI()).exists()) {
                BobbinConfig bobbinConfig = new ObjectMapper().readValue(new File(url.toURI()).getText(), BobbinConfig.class)
                bobbin = new Bobbin(bobbinConfig)
                bobbinThreadLocal.set(bobbin)
                return new BobbinNameAdapter(bobbin, name)
            } else {
                Util.report("Missing Bobbin.json at classpath")
                return new BobbinNameAdapter(new Bobbin(), name)
            }
        } else {
            return new BobbinNameAdapter(bobbin, name)
        }
    }
}
