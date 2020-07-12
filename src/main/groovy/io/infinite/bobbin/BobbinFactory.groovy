package io.infinite.bobbin


import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class BobbinFactory implements ILoggerFactory {

    @Override
    Logger getLogger(String name) {
        return new Bobbin(name)
    }

}
