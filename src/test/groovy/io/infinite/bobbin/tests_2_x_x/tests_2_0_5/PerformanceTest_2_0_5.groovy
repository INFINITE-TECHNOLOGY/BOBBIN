package io.infinite.bobbin.tests_2_x_x.tests_2_0_5

import groovy.util.logging.Slf4j
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.testng.annotations.Test

@Slf4j
class PerformanceTest_2_0_5  extends BobbinTest {


    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        log.info("Started")
        Thread t1 = Thread.start {
            Thread.currentThread().setName("THREAD 1")
            for (i in 1..1000000) {
                log.debug("ZZZ")
            }
        }
        Thread t2 = Thread.start {
            Thread.currentThread().setName("THREAD 2")
            for (i in 1..1000000) {
                log.debug("ZZZ")
            }
        }
        Thread.sleep(5000)
        t1.join()
        t2.join()
        log.info("Finished")
    }

    @Override
    void assertLogs() {

    }
}
