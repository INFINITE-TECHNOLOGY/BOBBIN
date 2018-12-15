package io.infinite.bobbin.tests_1_x_x

import groovy.text.Template
import io.infinite.bobbin.BobbinNameAdapter
import io.infinite.bobbin.TestTools

abstract class BobbinTest {

    String uuid = UUID.randomUUID().toString()
    Class thisClass = this.getClass()
    String className = thisClass.getSimpleName()
    String canonicalName = thisClass.getCanonicalName()
    BobbinNameAdapter bobbinNameAdapter

    final void setup() {
        Thread.currentThread().setName(this.getClass().getSimpleName())
        bobbinNameAdapter = new TestTools(thisClass.getResource("${className}.json").toURI()).getTestLogger(canonicalName) as BobbinNameAdapter
    }

    abstract void writeLogs()

    void test() {
        setup()
        writeLogs()
        assertLogs()
    }

    abstract void assertLogs()

    void assertFile(String fileName, String fileExtensionActual, String fileExtensionExpected) {
        File expectedResultsFile = new File(thisClass.getResource(fileName + fileExtensionExpected).toURI())
        Template expectedResultsTemplate = TestTools.simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert new File("./$fileName" + fileExtensionActual).getText() == expectedResultsTemplate.make(["uuid": uuid]).toString()
    }

}
