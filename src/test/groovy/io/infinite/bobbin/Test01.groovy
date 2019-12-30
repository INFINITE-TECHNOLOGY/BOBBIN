package io.infinite.bobbin

import groovy.util.logging.Slf4j
import org.testng.annotations.Test

@Slf4j
class Test01 {

    @Test
    void runTest() {
        log.debug("The Bobbin revolves infinitely.")
        log.debug("Welcome to the revolution in Java logging.")
        log.debug("Zero configuration output:")
        log.info("Log level 'info' - console")
        log.warn("Log level 'warn' - console, file")
        log.error("Log level 'error' - console, file", new Exception("Stacktrace is written only to file."))
    }

}
