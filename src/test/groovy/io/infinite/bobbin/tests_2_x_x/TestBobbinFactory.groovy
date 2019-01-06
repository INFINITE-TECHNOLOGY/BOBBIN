package io.infinite.bobbin.tests_2_x_x

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.BobbinConfig
import io.infinite.bobbin.BobbinFactory
import io.infinite.supplies.conf.ResourceLookupThread
import org.slf4j.Logger
import org.slf4j.helpers.Util

class TestBobbinFactory extends BobbinFactory {

    Class testedClass

    TestBobbinFactory(Class testedClass) {
        this.testedClass = testedClass
    }

    @Override
    Logger getLogger(String name) {
        Util.report("Getting test logger (Class name: ${testedClass.getCanonicalName()} Resource name: ${testedClass.getSimpleName()}.json)")
        getBobbinThreadLocal().clear()
        return super.getLogger(name)
    }

    BobbinConfig getBobbinConfig() {
        Util.report("Using test config: " + resolveName())
        BobbinConfig bobbinConfig = new ObjectMapper().readValue(
                new ResourceLookupThread("Bobbin", resolveName() + ".json", true).getResourceAsFile()
                , BobbinConfig.class
        )
        assert bobbinConfig != null
        return bobbinConfig
    }

    String resolveName() {
        String name = testedClass.getSimpleName()
        if (name == null) {
            return name
        }
        if (!name.startsWith("/")) {
            Class<?> c = testedClass
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
        return name
    }
}
