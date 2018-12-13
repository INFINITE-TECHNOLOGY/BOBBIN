package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.text.SimpleTemplateEngine
import org.slf4j.Logger
import org.slf4j.helpers.Util

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.security.AccessController
import java.security.PrivilegedAction

class TestTools {

    String bobbinConfFileName

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()

    TestTools(String bobbinConfFileName) {
        this.bobbinConfFileName = bobbinConfFileName
    }

    Logger getTestLogger(String name) {
        forceBobbinInit()
        return new BobbinNameAdapter(name)
    }

    static File getResourceFile(String fileName, String defaultFileName = null) {
        URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() {
            URL run() {
                ClassLoader threadCL = Thread.currentThread().getContextClassLoader()
                if (threadCL != null) {
                    return threadCL.getResource(fileName)
                } else {
                    return ClassLoader.getSystemResource(fileName)
                }
            }
        })
        File file = null
        if (url != null) {
            file = new File(url.toURI())
        } else {
            if (defaultFileName != null) {
                file = new File("./${defaultFileName}")
            }
        }
        return file
    }

    void forceBobbinInit() {
        File file = getResourceFile(bobbinConfFileName, "Bobbin.json")
        if (file.exists()) {
            BobbinConfig bobbinConfig = new ObjectMapper().readValue(file.getText(), BobbinConfig.class)
            Bobbin bobbin = new Bobbin(bobbinConfig)
            BobbinFactory.bobbinThreadLocal.set(bobbin)
        } else {
            Util.report("Missing Bobbin.json at classpath")
            BobbinFactory.bobbinThreadLocal.set(new Bobbin(new BobbinConfig()))
        }
    }


}
