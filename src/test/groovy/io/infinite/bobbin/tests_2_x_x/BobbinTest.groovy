package io.infinite.bobbin.tests_2_x_x

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinFactory
import io.infinite.bobbin.BobbinScriptEngineFactory
import io.infinite.bobbin.TestBobbinFactory
import org.slf4j.helpers.Util

abstract class BobbinTest {

    static SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()
    String uuid = UUID.randomUUID().toString()
    String dynamicValue = System.currentTimeMillis().toString()
    String className = this.getClass().getSimpleName()
    String canonicalName = this.getClass().getCanonicalName()
    Bobbin bobbinNameAdapter
    BobbinFactory bobbinFactory = new TestBobbinFactory()


    String stdout
    PrintStream initialPrintStream
    ByteArrayOutputStream byteArrayOutputStream


    String getConfName() {
        String name = getClass().getSimpleName()
        String result
        if (name == null) {
            result = name
        } else {
            if (!name.startsWith("/")) {
                Class<?> c = getClass()
                while (c.isArray()) {
                    c = c.getComponentType()
                }
                String baseName = c.getName()
                int index = baseName.lastIndexOf('.')
                if (index != -1) {
                    name = baseName.substring(0, index).replace('.', '/') + "/" + name
                }
            } else {
                name = name.substring(1)
            }
            result = name
        }
        result += ".json"
        Util.report("Using test config: " + result)
        return result
    }

    final void setup() {
        //BobbinThreadLocal.clear()
        //BobbinThreadLocal.setBobbinFactory(bobbinFactory)
        bobbinFactory.setConfName(getConfName())
        bobbinFactory.setBobbinConfig(bobbinFactory.initBobbinConfig())
        Thread.currentThread().setName(this.getClass().getSimpleName())
        bobbinNameAdapter = bobbinFactory.getLogger(canonicalName) as Bobbin
        bobbinNameAdapter.setBobbinScriptEngine(new BobbinScriptEngineFactory().bobbinScriptEngine)
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
