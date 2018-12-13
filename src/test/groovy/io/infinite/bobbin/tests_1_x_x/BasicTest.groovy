package io.infinite.bobbin.tests_1_x_x

import io.infinite.bobbin.BobbinNameAdapter
import io.infinite.bobbin.TestBobbinFactory
import org.junit.Test

class BasicTest {

    String uuid = UUID.randomUUID().toString()

    @Test
    void test() {
        Thread.currentThread().setName(this.getClass().getSimpleName())
        BobbinNameAdapter bobbinNameAdapter = new TestBobbinFactory().getLogger(this.getClass().getCanonicalName(), getBobbinStringConfig()) as BobbinNameAdapter
        bobbinNameAdapter.error("error abcd")
        bobbinNameAdapter.warn("warn 1234")
        bobbinNameAdapter.info("info abcd1234")
        bobbinNameAdapter.debug("debug " + uuid)
        bobbinNameAdapter.trace("trace " + uuid)
        println("Start test " + this.getClass().getSimpleName())
        assert new File("./LOGS/BasicTest/ALL_LEVELS/BasicTest.log").exists()
        assert new File("./LOGS/BasicTest/ALL_LEVELS/BasicTest.log").getText() == """error|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|error abcd
warn|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|warn 1234
info|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|info abcd1234
debug|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|debug $uuid
trace|BasicTest|io.infinite.bobbin.tests_1_x_x.BasicTest|trace $uuid
"""
    }

    String getBobbinStringConfig() {
        return """{
  "levels": "all",
  "classes": "all",
  "destinations": [
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileKey": "threadName + level",
        "fileName": "\\"./LOGS/\${threadName}/\${level}/\${threadName}_\${level}.log\\"",
        "zipFileName": "\\"./LOGS/\${threadName}/\${level}/ARCHIVE/\${threadName}_\${level}.zip\\"",
        "cleanupZipFileName": "\\"\${origFileName}_\${System.currentTimeMillis().toString()}.zip\\""
      },
      "format": "\\"\${level}|\${threadName}|\${className}|\${event.message}\\\\n\\""
    },
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileKey": "threadName + level",
        "fileName": "\\"./LOGS/\${threadName}/ALL_LEVELS/\${threadName}.log\\"",
        "zipFileName": "\\"./LOGS/\${threadName}/ALL_LEVELS/ARCHIVE/\${threadName}.zip\\"",
        "cleanupZipFileName": "\\"\${origFileName}_\${System.currentTimeMillis().toString()}.zip\\""
      },
      "format": "\\"\${level}|\${threadName}|\${className}|\${event.message}\\\\n\\""
    }
  ]
}
"""
    }

}
