package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.text.SimpleTemplateEngine
import org.slf4j.Logger
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

class TestTools {

    URI bobbinConfUri

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()

    TestTools(URI bobbinConfUri) {
        this.bobbinConfUri = bobbinConfUri
    }

    Logger getTestLogger(String name) {
        forceBobbinInit()
        return new BobbinNameAdapter(name)
    }

    void forceBobbinInit() {
        File file = new File(bobbinConfUri)
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
