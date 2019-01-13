package io.infinite.bobbin.tests_1_x_x.BasicTest


import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test

class BasicTest extends BobbinTest {

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
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/BasicTest/ALL_LEVELS/BasicTest.log", "LOGS/BasicTest/ALL_LEVELS/BasicTest.expected")
        assertFile("LOGS/BasicTest/trace/BasicTest_trace.log", "LOGS/BasicTest/trace/BasicTest_trace.expected")
        assertFile("LOGS/BasicTest/info/BasicTest_info.log", "LOGS/BasicTest/info/BasicTest_info.expected")
        assertFile("LOGS/BasicTest/debug/BasicTest_debug.log", "LOGS/BasicTest/debug/BasicTest_debug.expected")
        assertFile("LOGS/BasicTest/error/BasicTest_error.log", "LOGS/BasicTest/error/BasicTest_error.expected")
        assertFile("LOGS/BasicTest/warn/BasicTest_warn.log", "LOGS/BasicTest/warn/BasicTest.expected")
    }

}
