package org.slf4j.impl

import groovy.transform.CompileStatic
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.spi.MDCAdapter

@CompileStatic
public class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder()

    private StaticMDCBinder() {
    }

    public MDCAdapter getMDCA() {
        return new BasicMDCAdapter()
    }

    public String getMDCAdapterClassStr() {
        return BasicMDCAdapter.class.getName()
    }
}
