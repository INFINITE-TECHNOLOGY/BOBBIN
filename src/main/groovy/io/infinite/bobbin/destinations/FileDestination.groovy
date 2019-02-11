package io.infinite.bobbin.destinations

import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinFile
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.helpers.Util

@CompileStatic
class FileDestination extends Destination {

    BobbinFile currentBobbinFile

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    FileDestination(DestinationConfig destinationConfig) {
        super(destinationConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    protected void store(String finalOutputMessageText, Level level, String className, String date) {
        String newFileName = bobbinScriptEngine.evalFileName(level.value(), className, date)
        refreshCurrentFile(newFileName)
        currentBobbinFile.writer.write(finalOutputMessageText)
        currentBobbinFile.writer.flush()
    }

    void refreshCurrentFile(String newFileName) {
        if (currentBobbinFile == null) {
            currentBobbinFile = initFile(newFileName)
        } else {
            if (currentBobbinFile.fileName != newFileName) {
                currentBobbinFile = initFile(newFileName)
            }
        }
    }

    BobbinFile initFile(String fileName) {
        BobbinFile file = new BobbinFile(fileName)
        file.fileName = fileName
        file.getParentFile().mkdirs()
        file.writer = new FileWriter(file, true)
        return file
    }

    static {
        Util.report("Bobbin: " + Thread.currentThread().getName().padRight(16) + ": " + "application working dir: " + new BobbinFile("./").getCanonicalPath())
    }

}
