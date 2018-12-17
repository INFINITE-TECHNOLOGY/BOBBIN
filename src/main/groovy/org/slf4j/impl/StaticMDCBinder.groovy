package org.slf4j.impl

import io.infinite.bobbin.BobbinAdapter
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.spi.MDCAdapter

public class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder()

    private StaticMDCBinder() {
    }

    public MDCAdapter getMDCA() {
        return new BobbinAdapter()
    }

    public String getMDCAdapterClassStr() {
        return BobbinAdapter.class.getName()
    }
}
