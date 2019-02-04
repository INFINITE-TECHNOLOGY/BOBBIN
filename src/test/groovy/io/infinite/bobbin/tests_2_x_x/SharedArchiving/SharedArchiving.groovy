package io.infinite.bobbin.tests_2_x_x.SharedArchiving


import io.infinite.bobbin.BobbinThreadLocal
import io.infinite.bobbin.destinations.FileDestination
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import io.infinite.bobbin.tests_2_x_x.TestSharedFileDestination
import org.junit.Test

import java.util.zip.ZipFile

class SharedArchiving extends BobbinTest {

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        TestSharedFileDestination.getInstance().getEventQueueRunnable().setSharedDestination(new FileDestination(TestSharedFileDestination.getInstance().getDestinationConfig(), TestSharedFileDestination.getInstance().getParentBobbinConfig()))
        BobbinThreadLocal.getBobbin().error("ZIP","e abcd")
        BobbinThreadLocal.getBobbin().warn("ZIP", "w 1234")
        BobbinThreadLocal.getBobbin().info("LOG", "i abcd1234")
        BobbinThreadLocal.getBobbin().debug("LOG", "d " + uuid)
        BobbinThreadLocal.getBobbin().trace("LOG", "t " + uuid)
        //Thread.currentThread().sleep(1500)
        while (!TestSharedFileDestination.getInstance().getEventQueueRunnable().getEventQueue().isEmpty()) {
            Thread.sleep(200)
        }
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/SharedArchiving/TEST_LOG.log", "LOGS/SharedArchiving/TEST_LOG.expected")
        ZipFile zipFile = new ZipFile(new File("./LOGS/SharedArchiving/TEST_ZIP.log.zip"))
        assert zipFile.entries().toList().size() == 1
        zipFile.entries().each {
            assert zipFile.getInputStream(it).getText() == "error|SharedArchiving|ZIP|e abcd\nwarn|SharedArchiving|ZIP|w 1234\n"
        }
        assert !new File("./LOGS/SharedArchiving/ZIP.log").exists()
        assert !new File("./LOGS/SharedArchiving/LOG.log.zip").exists()
    }
}
