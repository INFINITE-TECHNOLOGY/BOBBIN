package io.infinite.bobbin.tests_2_x_x.MDC

import io.infinite.bobbin.tests_2_x_x.BobbinTest_2_x_x
import org.junit.Test

class MDCTest extends BobbinTest_2_x_x{

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
    }

    @Override
    void assertLogs() {
        //        assertFile("LOGS/BaseTest/ALL_LEVELS/BaseTest", ".log", ".expected")
    }
}
