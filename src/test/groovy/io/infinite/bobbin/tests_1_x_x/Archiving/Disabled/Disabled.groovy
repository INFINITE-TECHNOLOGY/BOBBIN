package io.infinite.bobbin.tests_1_x_x.Archiving.Disabled

import io.infinite.bobbin.BobbinThreadLocal
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test

class Disabled extends BobbinTest {

    @Override
    void writeLogs() {
        BobbinThreadLocal.getBobbin().error("ZIP", "error abcd")
        BobbinThreadLocal.getBobbin().warn("ZIP", "warn 1234")
        BobbinThreadLocal.getBobbin().info("LOG", "info abcd1234")
        BobbinThreadLocal.getBobbin().debug("LOG", "debug " + uuid)
        BobbinThreadLocal.getBobbin().trace("LOG", "trace " + uuid)
        Thread.currentThread().sleep(1500)
    }

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/Archiving/Disabled/LOG.log", "LOGS/Archiving/Disabled/LOG.expected")
        assertFile("LOGS/Archiving/Disabled/ZIP.log", "LOGS/Archiving/Disabled/ZIP.expected")
        assert !new File("./LOGS/Archiving/Disabled/ZIP.log.zip").exists()
        assert !new File("./LOGS/Archiving/Disabled/LOG.log.zip").exists()
    }

}
