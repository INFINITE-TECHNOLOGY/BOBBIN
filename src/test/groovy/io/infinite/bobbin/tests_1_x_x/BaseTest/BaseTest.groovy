package io.infinite.bobbin.tests_1_x_x.BaseTest

import groovy.text.Template
import io.infinite.bobbin.BobbinNameAdapter
import io.infinite.bobbin.TestTools
import org.junit.Test

class BaseTest {

    String uuid = UUID.randomUUID().toString()
    Class thisClass = this.getClass()
    String className = thisClass.getSimpleName()
    String canonicalName = thisClass.getCanonicalName()

    @Test
    void test() {//todo: getClass->this.getClass
        Thread.currentThread().setName(this.getClass().getSimpleName())
        BobbinNameAdapter bobbinNameAdapter = new TestTools(thisClass.getResource("${className}.json").toURI()).getTestLogger(canonicalName) as BobbinNameAdapter
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
        assertFile("LOGS/BaseTest/ALL_LEVELS/BaseTest", ".log", ".expected")
        assertFile("LOGS/BaseTest/trace/BaseTest_trace", ".log", ".expected")
        assertFile("LOGS/BaseTest/info/BaseTest_info", ".log", ".expected")
        assertFile("LOGS/BaseTest/debug/BaseTest_debug", ".log", ".expected")
        assertFile("LOGS/BaseTest/error/BaseTest_error", ".log", ".expected")
        assertFile("LOGS/BaseTest/warn/BaseTest_warn", ".log", ".expected")
    }

    void assertFile(String fileName, String fileExtensionActual, String fileExtensionExpected) {
        File expectedResultsFile = new File(thisClass.getResource(fileName + fileExtensionExpected).toURI())
        Template expectedResultsTemplate = TestTools.simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert new File("./$fileName" + fileExtensionActual).getText() == expectedResultsTemplate.make(["uuid": uuid]).toString()
    }

    String getBobbinConfFileName() {
        return this.getClass().getSimpleName() + ".json"
    }

}
