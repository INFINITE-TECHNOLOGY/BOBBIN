package io.infinite.bobbin.tests_1_x_x

import groovy.text.Template
import io.infinite.bobbin.BobbinNameAdapter
import io.infinite.bobbin.TestTools
import org.junit.Test

class BasicTest {

    String uuid = UUID.randomUUID().toString()

    @Test
    void test() {
        Thread.currentThread().setName(this.getClass().getSimpleName())
        BobbinNameAdapter bobbinNameAdapter = new TestTools(getBobbinConfFileName()).getTestLogger(this.getClass().getCanonicalName()) as BobbinNameAdapter
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
        File file = TestTools.getResourceFile("BasicTest.expected")
        assert file != null
        Template template = TestTools.simpleTemplateEngine.createTemplate(file)
        assert new File("./LOGS/BasicTest/ALL_LEVELS/BasicTest.log").exists()
        assert new File("./LOGS/BasicTest/ALL_LEVELS/BasicTest.log").getText() == template.make(["uuid": uuid]).toString()
    }

    String getBobbinConfFileName() {
        return this.getClass().getSimpleName() + ".json"
    }

}
