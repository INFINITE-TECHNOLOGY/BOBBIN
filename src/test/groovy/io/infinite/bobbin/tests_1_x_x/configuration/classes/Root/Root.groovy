package io.infinite.bobbin.tests_1_x_x.configuration.classes.Root

import io.infinite.bobbin.BobbinThreadLocal
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test

class Root extends BobbinTest {

    @Override
    void writeLogs() {
        BobbinThreadLocal.get().error("Enabled", "error abcd")
        BobbinThreadLocal.get().warn("Disabled", "warn 1234")
        BobbinThreadLocal.get().info("Enabled", "info abcd1234")
        BobbinThreadLocal.get().debug("Disabled", "Disabled", "debug " + uuid)
        BobbinThreadLocal.get().trace("Enabled", "trace " + uuid)
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
