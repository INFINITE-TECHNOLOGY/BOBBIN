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
        assertFile("LOGS/BaseTest/ALL_LEVELS/BaseTest.log")
        assertFile("LOGS/BaseTest/TRACE/BaseTest_trace.log")
        assertFile("LOGS/BaseTest/INFO/BaseTest_info.log")
        assertFile("LOGS/BaseTest/DEBUG/BaseTest_debug.log")
        assertFile("LOGS/BaseTest/ERROR/BaseTest_error.log")
        assertFile("LOGS/BaseTest/WARN/BaseTest_warn.log")
    }

    void assertFile(String fileName) {
        File file = new File(thisClass.getResource(fileName).toURI())
        Template template = TestTools.simpleTemplateEngine.createTemplate(file)
        assert new File("./$fileName").exists()
        assert new File("./$fileName").getText() == template.make(["uuid": uuid]).toString()
    }

    String getBobbinConfFileName() {
        return this.getClass().getSimpleName() + ".json"
    }

}
