package io.infinite.bobbin.tests_1_x_x.Archiving.Disabled

import io.infinite.bobbin.tests_1_x_x.BobbinTest_1_x_x
import org.junit.Test

class Disabled extends BobbinTest_1_x_x {

    @Override
    void writeLogs() {
        bobbinNameAdapter.bobbin().error("ZIP","error abcd")
        bobbinNameAdapter.bobbin().warn("ZIP", "warn 1234")
        bobbinNameAdapter.bobbin().info("LOG", "info abcd1234")
        bobbinNameAdapter.bobbin().debug("LOG", "debug " + uuid)
        bobbinNameAdapter.bobbin().trace("LOG", "trace " + uuid)
        Thread.currentThread().sleep(1500)
    }

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/Archiving/Disabled/LOG", ".log", ".expected")
        assertFile("LOGS/Archiving/Disabled/ZIP", ".log", ".expected")
        assert !new File("./LOGS/Archiving/Disabled/ZIP.log.zip").exists()
        assert !new File("./LOGS/Archiving/Disabled/LOG.log.zip").exists()
    }

}
