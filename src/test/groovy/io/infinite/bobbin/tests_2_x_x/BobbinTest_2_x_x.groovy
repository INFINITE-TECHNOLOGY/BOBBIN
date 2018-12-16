package io.infinite.bobbin.tests_2_x_x

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import io.infinite.bobbin.BobbinAdapter

abstract class BobbinTest_2_x_x {

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()
    String uuid = UUID.randomUUID().toString()
    String dynamicValue = System.currentTimeMillis().toString()
    Class thisClass = this.getClass()
    String className = thisClass.getSimpleName()
    String canonicalName = thisClass.getCanonicalName()
    BobbinAdapter bobbinNameAdapter

    final void setup() {
        Thread.currentThread().setName(this.getClass().getSimpleName())
        bobbinNameAdapter = new TestBobbinFactory(thisClass).getLogger(canonicalName) as BobbinAdapter
    }

    abstract void writeLogs()

    final void runTest() {
        setup()
        writeLogs()
        assertLogs()
    }

    abstract void assertLogs()

    void assertFile(String actualFileName, String expectedFileName) {
        assert thisClass.getResource(expectedFileName) != null
        File expectedResultsFile = new File(thisClass.getResource(expectedFileName).toURI())
        Template expectedResultsTemplate = simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert new File("./" + actualFileName).getText() == expectedResultsTemplate.make(["uuid": uuid, "dynamicValue": dynamicValue]).toString()
    }

}
