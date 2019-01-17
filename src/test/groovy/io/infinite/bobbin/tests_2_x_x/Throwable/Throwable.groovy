package io.infinite.bobbin.tests_2_x_x.Throwable

import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test
import org.slf4j.MDC

class Throwable extends BobbinTest {


    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        try {
            throw new Exception("test exception message")
        } catch (Exception e) {
            bobbinNameAdapter.error(uuid, e)
        }

    }

    @Override
    void assertLogs() {
        assertFile("LOGS/Throwable/Throwable.log", "LOGS/Throwable/Throwable.expected")
    }
}
