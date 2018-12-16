package io.infinite.bobbin.tests_1_x_x.Archiving.Enabled

import io.infinite.bobbin.tests_1_x_x.BobbinTest
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipFile

class Enabled extends BobbinTest {

    @Override
    void writeLogs() {
        bobbinNameAdapter.bobbin().error("ZIP","error abcd")
        bobbinNameAdapter.bobbin().warn("ZIP", "warn 1234")
        bobbinNameAdapter.bobbin().info("LOG", "info abcd1234")
        bobbinNameAdapter.bobbin().debug("LOG", "debug " + uuid)
        bobbinNameAdapter.bobbin().trace("LOG", "trace " + uuid)
        Thread.currentThread().sleep(1500)
    }

    @Test
    void test() {
        super.runTest()
    }

    @Override
    void assertLogs() {
        assertFile("LOGS/Archiving/Enabled/LOG", ".log", ".expected")
        ZipFile zipFile = new ZipFile(new File("./LOGS/Archiving/Enabled/ZIP.log.zip"))
        assert zipFile.entries().toList().size() == 1
        zipFile.entries().each {
            assert zipFile.getInputStream(it).getText() == "error|Enabled|ZIP|error abcd\nwarn|Enabled|ZIP|warn 1234\n"
        }
        assert !new File("./LOGS/Archiving/Enabled/ZIP.log").exists()
    }

}
