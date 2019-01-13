package io.infinite.bobbin.tests_2_x_x


import io.infinite.bobbin.BobbinFactory
import io.infinite.bobbin.BobbinThreadLocal
import org.slf4j.Logger
import org.slf4j.helpers.Util

class TestBobbinFactory extends BobbinFactory {

    @Override
    Logger getLogger(String name) {
        BobbinThreadLocal.clear()
        setBobbinConfig(initBobbinConfig())
        return super.getLogger(name)
    }

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
}
