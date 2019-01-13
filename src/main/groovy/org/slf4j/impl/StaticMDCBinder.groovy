package org.slf4j.impl

import io.infinite.bobbin.Bobbin
import io.infinite.bobbin.BobbinThreadLocal
import org.slf4j.spi.MDCAdapter

public class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder()

    private StaticMDCBinder() {
    }

    public MDCAdapter getMDCA() {
        return BobbinThreadLocal.get()
    }

    public String getMDCAdapterClassStr() {
        return Bobbin.class.getName()
    }
}
