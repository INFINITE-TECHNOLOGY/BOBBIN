package org.slf4j.impl

import io.infinite.bobbin.BobbinFactory
import org.slf4j.ILoggerFactory
import org.slf4j.spi.LoggerFactoryBinder

class StaticLoggerBinder implements LoggerFactoryBinder {

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder()

    static final StaticLoggerBinder getSingleton() {
        return SINGLETON
    }

    public static String REQUESTED_API_VERSION = "1.6.99" // !final

    private static final String loggerFactoryClassStr = BobbinFactory.name

    private final ILoggerFactory loggerFactory

    private StaticLoggerBinder() {
        loggerFactory = new BobbinFactory()
    }

    ILoggerFactory getLoggerFactory() {
        return loggerFactory
    }

    String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr
    }
}