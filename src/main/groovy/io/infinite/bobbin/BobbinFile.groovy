package io.infinite.bobbin

import java.time.Instant

class BobbinFile extends File {

    FileWriter writer
    String fileName
    Instant createDate = Instant.now()

    BobbinFile(String pathname) {
        super(pathname)
    }

    BobbinFile(String parent, String child) {
        super(parent, child)
    }

    BobbinFile(File parent, String child) {
        super(parent, child)
    }

    BobbinFile(URI uri) {
        super(uri)
    }

}
