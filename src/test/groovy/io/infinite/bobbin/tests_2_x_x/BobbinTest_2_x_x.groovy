package io.infinite.bobbin.tests_2_x_x

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import io.infinite.bobbin.BobbinAdapter
import org.junit.Rule
import org.junit.rules.TemporaryFolder

abstract class BobbinTest_2_x_x {

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()
    String uuid = UUID.randomUUID().toString()
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

    void assertFile(String fileName, String fileExtensionActual, String fileExtensionExpected) {
        assert thisClass.getResource(fileName + fileExtensionExpected) != null
        File expectedResultsFile = new File(thisClass.getResource(fileName + fileExtensionExpected).toURI())
        Template expectedResultsTemplate = simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert new File("./$fileName" + fileExtensionActual).getText() == expectedResultsTemplate.make(["uuid": uuid]).toString()
    }

}
