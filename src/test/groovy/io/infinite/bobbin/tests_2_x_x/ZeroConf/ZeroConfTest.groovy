package io.infinite.bobbin.tests_2_x_x.ZeroConf

import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinThreadLocal
import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.junit.Test

class ZeroConfTest extends BobbinTest {


    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        Bobbin bobbin = BobbinThreadLocal.getBobbin() as Bobbin
        bobbin.getDestinations()[0].getDestinationConfig().setFormat("\"\${level}|\${threadName}|\${className}|\${event.message}\\n\"")
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
        System.out.flush()
    }

    @Override
    void assertLogs() {
        assertStdout("STDOUT/stdout.expected")
    }
}
