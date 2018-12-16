package io.infinite.bobbin.tests_1_x_x

import groovy.text.Template
import io.infinite.bobbin.BobbinAdapter
import io.infinite.bobbin.tests_2_x_x.TestBobbinFactory
import org.junit.Rule
import org.junit.rules.TemporaryFolder

abstract class BobbinTest_1_x_x {

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
        Template expectedResultsTemplate = TestTools.simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert new File("./$fileName" + fileExtensionActual).getText() == expectedResultsTemplate.make(["uuid": uuid]).toString()
    }

}
