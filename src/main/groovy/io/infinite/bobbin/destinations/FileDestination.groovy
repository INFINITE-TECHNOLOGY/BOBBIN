package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinFile
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.helpers.Util

class FileDestination extends Destination {

    ThreadLocal<BobbinFile> bobbinFileMap = new ThreadLocal<BobbinFile>()

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    FileDestination(DestinationConfig destinationConfig) {
        super(destinationConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    protected void store(String finalOutputMessageText, Level level, String className, String date) {
        String newFileName = bobbinScriptEngine.evalFileName(level.value(), className, date)
        refreshCurrentFile(newFileName)
        bobbinFileMap.get().writer.write(finalOutputMessageText)
        bobbinFileMap.get().writer.flush()
    }

    void refreshCurrentFile(String newFileName) {
        if (bobbinFileMap.get() == null) {
            bobbinFileMap.set(initFile(newFileName))
        } else {
            if (bobbinFileMap.get().fileName != newFileName) {
                bobbinFileMap.set(initFile(newFileName))
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
