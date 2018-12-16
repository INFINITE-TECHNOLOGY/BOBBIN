package io.infinite.bobbin.tests_1_x_x

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.text.SimpleTemplateEngine
import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinAdapter
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.factories.BobbinFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

class TestTools {

    URI bobbinConfUri

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()

    TestTools(URI bobbinConfUri) {
        this.bobbinConfUri = bobbinConfUri
    }

    Logger getTestLogger(String name) {
        forceBobbinInit()
        return new BobbinAdapter(name)
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
