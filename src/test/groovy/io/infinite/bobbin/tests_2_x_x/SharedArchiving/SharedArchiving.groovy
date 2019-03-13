package io.infinite.bobbin.tests_2_x_x.SharedArchiving

import io.infinite.bobbin.BobbinFactory
import io.infinite.bobbin.destinations.FileDestination
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import io.infinite.bobbin.tests_2_x_x.TestSharedFileDestination
import org.testng.annotations.Test
import org.slf4j.Logger

import java.util.zip.ZipFile

class SharedArchiving extends BobbinTest {

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        //TestSharedFileDestination.getInstance().getEventQueueRunnable().setSharedDestination(new FileDestination(TestSharedFileDestination.getInstance().getDestinationConfig(), TestSharedFileDestination.getInstance().getParentBobbinConfig()))
        Logger zipLogger = bobbinFactory.getLogger("ZIP")
        Logger logLogger = bobbinFactory.getLogger("LOG")
        zipLogger.error("e abcd")
        zipLogger.warn("w 1234")
        logLogger.info("i abcd1234")
        logLogger.debug("d " + uuid)
        logLogger.trace("t " + uuid)
        //Thread.currentThread().sleep(1500)
        /*while (!TestSharedFileDestination.getInstance().getEventQueueRunnable().getEventQueue().isEmpty()) {
            Thread.sleep(200)
        }*/
    }

    @Override
    void assertLogs() {
        return
        /*assertFile("LOGS/SharedArchiving/TEST_LOG.log", "LOGS/SharedArchiving/TEST_LOG.expected")
        ZipFile zipFile = new ZipFile(new File("./LOGS/SharedArchiving/TEST_ZIP.log.zip"))
        assert zipFile.entries().toList().size() == 1
        zipFile.entries().each {
            assert zipFile.getInputStream(it).getText() == "error|SharedArchiving|ZIP|e abcd\nwarn|SharedArchiving|ZIP|w 1234\n"
        }
        assert !new File("./LOGS/SharedArchiving/ZIP.log").exists()
        assert !new File("./LOGS/SharedArchiving/LOG.log.zip").exists()*/
    }
}
