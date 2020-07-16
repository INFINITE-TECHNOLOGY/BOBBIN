package io.infinite.bobbin

import groovy.util.logging.Slf4j
import org.slf4j.MDC

@Slf4j
class Test02 {

    static void main(String[] args) {
        (0..5).each {
            log.warn(
                    "A",
                    "B",
                    "C",
                    "D",
                    "E",
                    "F",
                    "G"
            )
        }
    }

}
