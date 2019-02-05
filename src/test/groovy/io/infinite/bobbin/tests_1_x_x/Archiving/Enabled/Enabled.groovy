package io.infinite.bobbin.tests_1_x_x.Archiving.Enabled

import io.infinite.bobbin.BobbinFactory
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test
import org.slf4j.Logger

import java.util.zip.ZipFile

class Enabled extends BobbinTest {

    @Override
    void writeLogs() {
        Logger zipLogger = bobbinFactory.getLogger("ZIP")
        Logger logLogger = bobbinFactory.getLogger("LOG")
        zipLogger.error("error abcd")
        zipLogger.warn("warn 1234")
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
        assertFile("LOGS/Archiving/Enabled/LOG.log", "LOGS/Archiving/Enabled/LOG.expected")
        ZipFile zipFile = new ZipFile(new File("./LOGS/Archiving/Enabled/ZIP.log.zip"))
        assert zipFile.entries().toList().size() == 1
        zipFile.entries().each {
            assert zipFile.getInputStream(it).getText() == "error|Enabled|ZIP|error abcd\nwarn|Enabled|ZIP|warn 1234\n"
        }
        assert !new File("./LOGS/Archiving/Enabled/ZIP.log").exists()
        assert !new File("./LOGS/Archiving/Enabled/LOG.log.zip").exists()
    }

}
