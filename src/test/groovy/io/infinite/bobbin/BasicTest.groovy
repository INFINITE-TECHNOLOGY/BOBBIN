package io.infinite.bobbin

import groovy.util.logging.Slf4j
import org.junit.Test

@Slf4j
class BasicTest {

    @Test
    void test() {
        (0..10000).each {
            log.debug("Debug $it")
        }
    }

}
