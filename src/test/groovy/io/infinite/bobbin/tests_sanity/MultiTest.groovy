package io.infinite.bobbin.tests_sanity

import groovy.util.logging.Slf4j
import org.junit.Test

@Slf4j
class MultiTest {

    @Test
    void test() {
        (0..5).each {
            log.debug("Debug $it")
        }
    }

}
