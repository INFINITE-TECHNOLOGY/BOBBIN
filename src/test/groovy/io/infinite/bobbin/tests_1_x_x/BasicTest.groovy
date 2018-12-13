package io.infinite.bobbin.tests_1_x_x


import io.infinite.bobbin.BobbinNameAdapter
import io.infinite.bobbin.TestBobbinFactory
import org.junit.Test

class BasicTest {

    String uuid = UUID.randomUUID().toString()

    @Test
    void test() {
        Thread.currentThread().setName(this.getClass().getSimpleName())
        BobbinNameAdapter bobbinNameAdapter = new TestBobbinFactory(getBobbinConfFileName()).getTestLogger(this.getClass().getCanonicalName()) as BobbinNameAdapter
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
        assert new File("./LOGS/BasicTest/ALL_LEVELS/BasicTest.log").exists()
        assert new File("./LOGS/BasicTest/ALL_LEVELS/BasicTest.log").getText() == """error|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|error abcd
warn|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|warn 1234
info|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|info abcd1234
debug|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|debug $uuid
trace|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|trace $uuid
""".toString()
    }

    String getBobbinConfFileName() {
        return this.getClass().getSimpleName() + ".json"
    }

}
