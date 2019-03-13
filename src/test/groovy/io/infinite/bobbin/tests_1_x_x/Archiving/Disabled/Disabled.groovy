package io.infinite.bobbin.tests_1_x_x.Archiving.Disabled


import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.testng.annotations.Test
import org.slf4j.Logger

class Disabled extends BobbinTest {

    @Override
    void writeLogs() {
        Logger zipLogger = bobbinFactory.getLogger("ZIP")
        zipLogger.error("error abcd")
        zipLogger.warn("warn 1234")
        Logger logLogger = bobbinFactory.getLogger("LOG")
        logLogger.info("info abcd1234")
        logLogger.debug("debug " + uuid)
        logLogger.trace("trace " + uuid)
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
