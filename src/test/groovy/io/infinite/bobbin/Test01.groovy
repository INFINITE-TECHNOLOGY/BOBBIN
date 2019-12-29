package io.infinite.bobbin

import groovy.util.logging.Slf4j
import org.testng.annotations.Test

@Slf4j
class Test01 {

    @Test
    runTest() {
        log.debug("Bobbin is the best.")
        assert false
    }

}
