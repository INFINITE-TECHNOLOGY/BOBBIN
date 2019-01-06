package io.infinite.bobbin.tests_1_x_x


import groovy.text.SimpleTemplateEngine

class TestTools {

    URI bobbinConfUri

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()

    TestTools(URI bobbinConfUri) {
        this.bobbinConfUri = bobbinConfUri
    }


}
