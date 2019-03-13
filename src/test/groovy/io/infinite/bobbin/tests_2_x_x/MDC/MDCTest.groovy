package io.infinite.bobbin.tests_2_x_x.MDC

import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.testng.annotations.Test
import org.slf4j.MDC

class MDCTest extends BobbinTest {


    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        MDC.put("dynamicValue", dynamicValue)
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/MDC/${dynamicValue}.log", "LOGS/MDC/MDC.expected")
    }
}
