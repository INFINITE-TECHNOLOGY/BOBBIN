package io.infinite.bobbin

import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

class BobbinSlf4jBinding implements SLF4JServiceProvider {

    public static final String REQUESTED_API_VERSION = "1.8.99"

    private ILoggerFactory loggerFactory
    private IMarkerFactory markerFactory
    private MDCAdapter mdcAdapter

    @Override
    ILoggerFactory getLoggerFactory() {
        return loggerFactory
    }

    @Override
    IMarkerFactory getMarkerFactory() {
        return markerFactory
    }

    @Override
    MDCAdapter getMDCAdapter() {
        return mdcAdapter
    }

    @Override
    String getRequesteApiVersion() {
        return REQUESTED_API_VERSION
    }

    @Override
    void initialize() {
        markerFactory = new BasicMarkerFactory()
        loggerFactory = new BobbinFactory()
        mdcAdapter = new BasicMDCAdapter()
    }

}
