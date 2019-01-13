package io.infinite.bobbin.tests_2_x_x

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import io.infinite.bobbin.BobbinAdapter

abstract class BobbinTest extends TestBobbinFactory {

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()
    String uuid = UUID.randomUUID().toString()
    String dynamicValue = System.currentTimeMillis().toString()
    String className = this.getClass().getSimpleName()
    String canonicalName = this.getClass().getCanonicalName()
    BobbinAdapter bobbinNameAdapter


    String stdout
    PrintStream initialPrintStream
    ByteArrayOutputStream byteArrayOutputStream

    final void setup() {
        Thread.currentThread().setName(this.getClass().getSimpleName())
        bobbinNameAdapter = getLogger(canonicalName) as BobbinAdapter
        byteArrayOutputStream = new ByteArrayOutputStream()
        PrintStream printStream = new PrintStream(byteArrayOutputStream)
        initialPrintStream = System.out
        System.setOut(printStream)
    }

    abstract void writeLogs()

    final void runTest() {
        setup()
        writeLogs()
        finalize()
        assertLogs()
    }

    void finalize() {
        System.setOut(initialPrintStream)
        stdout = byteArrayOutputStream.toString()
    }

    abstract void assertLogs()

    void assertFile(String actualFileName, String expectedFileName) {
        assert this.getClass().getResource(expectedFileName) != null
        File expectedResultsFile = new File(this.getClass().getResource(expectedFileName).toURI())
        Template expectedResultsTemplate = simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert new File("./" + actualFileName).getText() == expectedResultsTemplate.make(["uuid": uuid, "dynamicValue": dynamicValue]).toString()
    }

    void assertStdout(String expectedFileName) {
        assert this.getClass().getResource(expectedFileName) != null
        File expectedResultsFile = new File(this.getClass().getResource(expectedFileName).toURI())
        Template expectedResultsTemplate = simpleTemplateEngine.createTemplate(expectedResultsFile)
        assert stdout == expectedResultsTemplate.make(["uuid": uuid, "dynamicValue": dynamicValue]).toString()
    }

}
