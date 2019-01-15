package io.infinite.bobbin.tests_1_x_x.configuration.classes.Root

import io.infinite.bobbin.BobbinThreadLocal
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test

class Root extends BobbinTest {

    @Override
    void writeLogs() {
        BobbinThreadLocal.getBobbin().error("Enabled", "error abcd")
        BobbinThreadLocal.getBobbin().warn("Disabled", "warn 1234")
        BobbinThreadLocal.getBobbin().info("Enabled", "info abcd1234")
        BobbinThreadLocal.getBobbin().debug("Disabled", "Disabled", "debug " + uuid)
        BobbinThreadLocal.getBobbin().trace("Enabled", "trace " + uuid)
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
