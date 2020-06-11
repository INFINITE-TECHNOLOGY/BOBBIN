package org.slf4j.impl

import groovy.transform.CompileDynamic
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.spi.MDCAdapter

class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder()

    private StaticMDCBinder() {
    }

    MDCAdapter getMDCA() {
        return new BasicMDCAdapter()
    }

    String getMDCAdapterClassStr() {
        return BasicMDCAdapter.class.getName()
    }
}
