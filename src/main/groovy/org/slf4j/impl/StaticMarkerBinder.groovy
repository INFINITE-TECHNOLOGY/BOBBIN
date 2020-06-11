package org.slf4j.impl

import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MarkerFactoryBinder

class StaticMarkerBinder implements MarkerFactoryBinder {


    public static final StaticMarkerBinder SINGLETON = new StaticMarkerBinder()

    final IMarkerFactory markerFactory = new BasicMarkerFactory()

    private StaticMarkerBinder() {
    }

    IMarkerFactory getMarkerFactory() {
        return markerFactory
    }

    String getMarkerFactoryClassStr() {
        return BasicMarkerFactory.name
    }

}
