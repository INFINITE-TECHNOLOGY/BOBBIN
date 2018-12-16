package io.infinite.bobbin.tests_2_x_x

import io.infinite.bobbin.factories.BobbinFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

class TestBobbinFactory extends BobbinFactory {

    Class testedClass

    TestBobbinFactory(Class testedClass) {
        setTestedClass(testedClass)
    }

    @Override
    Logger getLogger(String name) {
        Util.report("Getting test logger (Class name: ${testedClass.getCanonicalName()}; Resource name: ${testedClass.getSimpleName()}.json)")
        getBobbinThreadLocal().clear()
        return super.getLogger(name)
    }

    @Override
    URL getResource() {
        return testedClass.getResource(testedClass.getSimpleName() + ".json")
    }
}
