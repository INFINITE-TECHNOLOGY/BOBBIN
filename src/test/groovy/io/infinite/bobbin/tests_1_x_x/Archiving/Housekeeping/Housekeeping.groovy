package io.infinite.bobbin.tests_1_x_x.Archiving.Housekeeping

import io.infinite.bobbin.BobbinFactory
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger

import java.util.zip.ZipFile

class Housekeeping extends BobbinTest {

    @Before
    void init() {
        File file = new File("./LOGS/Archiving/Housekeeping/LOG.log")
        file.getParentFile().mkdirs()
        FileWriter fileWriter = new FileWriter(file, true)
        fileWriter.write(uuid)
        fileWriter.flush()
        fileWriter.close()
    }

    @Override
    void writeLogs() {
        Logger logLogger = bobbinFactory.getLogger("LOG")
        logLogger.error("error " + uuid)
        logLogger.warn("warn " + uuid)
        Thread.currentThread().sleep(1500)
    }

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/Archiving/Housekeeping/LOG.log", "LOGS/Archiving/Housekeeping/LOG.expected")
        ZipFile zipFile = new ZipFile(new File("./LOGS/Archiving/Housekeeping/LOG.log_previous.zip"))
        assert zipFile.entries().toList().size() == 1
        zipFile.entries().each {
            assert zipFile.getInputStream(it).getText() == uuid
        }
    }

}
