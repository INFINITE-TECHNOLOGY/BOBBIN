package io.infinite.bobbin.tests_1_x_x.configuration.classes.Root

import io.infinite.bobbin.BobbinFactory
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.testng.annotations.Test
import org.slf4j.Logger

class Root extends BobbinTest {

    @Override
    void writeLogs() {
        Logger enabledLogger = bobbinFactory.getLogger("Enabled")
        Logger disabledLogger = bobbinFactory.getLogger("Disabled")
        enabledLogger.error("error abcd")
        disabledLogger.warn("warn 1234")
        enabledLogger.info("info abcd1234")
        disabledLogger.debug("Disabled", "debug " + uuid)
        enabledLogger.trace("trace " + uuid)
    }

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/configuration/classes/Root/Root.log", "LOGS/configuration/classes/Root/Root.expected")
    }

}
