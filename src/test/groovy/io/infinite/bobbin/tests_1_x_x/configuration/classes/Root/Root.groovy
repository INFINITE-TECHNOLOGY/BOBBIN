package io.infinite.bobbin.tests_1_x_x.configuration.classes.Root

import io.infinite.bobbin.tests_1_x_x.BobbinTest_1_x_x
import org.junit.Test

class Root extends BobbinTest_1_x_x {

    @Override
    void writeLogs() {
        bobbinNameAdapter.bobbin().error("Enabled","error abcd")
        bobbinNameAdapter.bobbin().warn("Disabled", "warn 1234")
        bobbinNameAdapter.bobbin().info("Enabled", "info abcd1234")
        bobbinNameAdapter.bobbin().debug("Disabled", "Disabled", "debug " + uuid)
        bobbinNameAdapter.bobbin().trace("Enabled", "trace " + uuid)
    }

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/configuration/classes/Root/Root", ".log", ".expected")
    }

}
