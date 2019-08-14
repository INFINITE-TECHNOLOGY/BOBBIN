package io.infinite.bobbin

class BobbinFile extends File {

    FileWriter writer
    String fileName
    Date createDate = new Date()

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
