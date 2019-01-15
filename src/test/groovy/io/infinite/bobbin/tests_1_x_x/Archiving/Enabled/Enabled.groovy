package io.infinite.bobbin.tests_1_x_x.Archiving.Enabled

import io.infinite.bobbin.BobbinThreadLocal
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test

import java.util.zip.ZipFile

class Enabled extends BobbinTest {

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
