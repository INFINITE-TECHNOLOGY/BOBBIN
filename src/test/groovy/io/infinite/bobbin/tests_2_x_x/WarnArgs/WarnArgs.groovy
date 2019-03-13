package io.infinite.bobbin.tests_2_x_x.WarnArgs

import io.infinite.bobbin.tests_2_x_x.BobbinTest
import org.testng.annotations.Test

class WarnArgs extends BobbinTest {


    @Test
    void test() {
        super.runTest()
    }

    @Override
    void writeLogs() {
        bobbinNameAdapter.warn("This is warn")
    }

    @Override
    void assertLogs() {
        println(stdout)
        //assertStdout("STDOUT/stdout.expected")
    }
}
