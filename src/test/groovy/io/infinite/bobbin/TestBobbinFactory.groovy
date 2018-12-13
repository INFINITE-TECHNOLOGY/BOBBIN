package io.infinite.bobbin

class TestBobbinFactory extends BobbinFactory {

    String bobbinConfigFileName

    TestBobbinFactory(String bobbinConfigFileName) {
        this.bobbinConfigFileName = bobbinConfigFileName
    }

    @Override
    String getBobbinConfigFileName() {
        return bobbinConfigFileName
    }
}
