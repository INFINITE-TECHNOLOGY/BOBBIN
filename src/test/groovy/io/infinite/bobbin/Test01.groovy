package io.infinite.bobbin

import groovy.util.logging.Slf4j
import org.slf4j.MDC

@Slf4j
class Test01 {

    static void main(String[] args) {
        MDC.put("Infinite", "TECH")
        log.debug("The Bobbin revolves infinitely.")
        log.debug("Welcome to the revolution in Java logging.")
        log.debug("Zero configuration output:")
        log.info("Log level 'info' - console")
        log.warn("Log level 'warn' - console, file")
        log.error("Log level 'error' - console, file", new Exception("a scary bug!"))
    }

}
