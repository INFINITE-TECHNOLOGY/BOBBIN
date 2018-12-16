package io.infinite.bobbin.tests_2_x_x.MDC

import io.infinite.bobbin.tests_2_x_x.BobbinTest_2_x_x
import org.junit.Test
import org.slf4j.MDC

class MDCTest extends BobbinTest_2_x_x {


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
        simpleTemplateEngine
        assertFile("LOGS/MDC/${dynamicValue}.log", "LOGS/MDC/MDC.log")
    }
}
