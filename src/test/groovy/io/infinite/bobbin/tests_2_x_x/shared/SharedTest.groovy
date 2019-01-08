package io.infinite.bobbin.tests_2_x_x.shared

import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.destinations.FileDestination
import io.infinite.bobbin.destinations.SharedFileDestination
import io.infinite.bobbin.tests_2_x_x.BobbinTest_2_x_x
import io.infinite.bobbin.tests_2_x_x.TestSharedFileDestination
import org.junit.Test
import org.slf4j.MDC

import java.text.SimpleDateFormat

class SharedTest extends BobbinTest_2_x_x {

    Date testDate = new Date()

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        TestSharedFileDestination.getInstance().getEventQueueRunnable().setFileDestination(new FileDestination(TestSharedFileDestination.getInstance().getDestinationConfig(), TestSharedFileDestination.getInstance().getParentBobbinConfig()))
        bobbinNameAdapter.error("Parent Thread error abcd" + uuid)
        bobbinNameAdapter.warn("Parent Thread warn 1234" + uuid)
        bobbinNameAdapter.info("Parent Thread info abcd1234" + uuid)
        bobbinNameAdapter.debug("Parent Thread debug " + uuid)
        bobbinNameAdapter.trace("Parent Thread trace " + uuid)
        while (!TestSharedFileDestination.getInstance().getEventQueueRunnable().getEventQueue().isEmpty()) {
            Thread.sleep(200)
        }
        Bobbin testBobbin = bobbinNameAdapter.bobbinFactory.bobbinThreadLocal.get() as Bobbin
        Thread.start {
            TestSharedFileDestination.getInstance().getEventQueueRunnable().setFileDestination(new FileDestination(TestSharedFileDestination.getInstance().getDestinationConfig(), TestSharedFileDestination.getInstance().getParentBobbinConfig()))
            Thread.currentThread().setName("test thread 1")
            bobbinNameAdapter.bobbinFactory.bobbinThreadLocal.set(testBobbin)
            bobbinNameAdapter.error("Thread 1 error abcd" + uuid)
            bobbinNameAdapter.warn("Thread 1 warn 1234" + uuid)
            bobbinNameAdapter.info("Thread 1 info abcd1234" + uuid)
            bobbinNameAdapter.debug("Thread 1 debug " + uuid)
            bobbinNameAdapter.trace("Thread 1 trace " + uuid)
            while (!TestSharedFileDestination.getInstance().getEventQueueRunnable().getEventQueue().isEmpty()) {
                Thread.sleep(200)
            }
        }.join()
        Thread.start {
            TestSharedFileDestination.getInstance().getEventQueueRunnable().setFileDestination(new FileDestination(TestSharedFileDestination.getInstance().getDestinationConfig(), TestSharedFileDestination.getInstance().getParentBobbinConfig()))
            Thread.currentThread().setName("test thread 2")
            bobbinNameAdapter.bobbinFactory.bobbinThreadLocal.set(testBobbin)
            bobbinNameAdapter.error("Thread 2 error abcd" + uuid)
            bobbinNameAdapter.warn("Thread 2 warn 1234" + uuid)
            bobbinNameAdapter.info("Thread 2 info abcd1234" + uuid)
            bobbinNameAdapter.debug("Thread 2 debug " + uuid)
            bobbinNameAdapter.trace("Thread 2 trace " + uuid)
            while (!TestSharedFileDestination.getInstance().getEventQueueRunnable().getEventQueue().isEmpty()) {
                Thread.sleep(200)
            }
        }.join()
        while (!TestSharedFileDestination.getInstance().getEventQueueRunnable().getEventQueue().isEmpty()) {
            Thread.sleep(200)
        }
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/ALL/WARNINGS_AND_ERRORS_${new SimpleDateFormat("yyyy-MM-dd").format(testDate)}.log", "LOGS/ALL/shared.expected")
    }
}
