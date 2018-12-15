package io.infinite.bobbin.tests_1_x_x.BaseTest

import io.infinite.bobbin.tests_1_x_x.BobbinTest
import org.junit.Test

class BaseTest extends BobbinTest {

    @Override
    void writeLogs() {
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
    }

    @Test
    void test() {
        super.test()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/BaseTest/ALL_LEVELS/BaseTest", ".log", ".expected")
        assertFile("LOGS/BaseTest/trace/BaseTest_trace", ".log", ".expected")
        assertFile("LOGS/BaseTest/info/BaseTest_info", ".log", ".expected")
        assertFile("LOGS/BaseTest/debug/BaseTest_debug", ".log", ".expected")
        assertFile("LOGS/BaseTest/error/BaseTest_error", ".log", ".expected")
        assertFile("LOGS/BaseTest/warn/BaseTest_warn", ".log", ".expected")
    }

}
