package io.infinite.bobbin.destinations

import io.infinite.bobbin.BobbinFile
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.helpers.Util

class FileDestination extends Destination {

    ThreadLocal<Map<Level, BobbinFile>> bobbinFileThreadLocalMap = new ThreadLocal<Map<Level, BobbinFile>>()

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    FileDestination(DestinationConfig destinationConfig) {
        super(destinationConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    protected void store(String finalOutputMessageText, Level level, String className, String date) {
        String newFileName = bobbinScriptEngine.evalFileName(level.value(), className, date)
        BobbinFile bobbinFile = refreshCurrentFile(level, newFileName)
        synchronized (newFileName) {
            bobbinFile.writer.write(finalOutputMessageText)
            bobbinFile.writer.flush()
        }
    }

    BobbinFile refreshCurrentFile(Level level, String newFileName) {
        Map<Level, BobbinFile> bobbinFileMap = bobbinFileThreadLocalMap.get()
        if (bobbinFileMap == null) {
            bobbinFileMap = new HashMap<Level, BobbinFile>()
            BobbinFile bobbinFile = initFile(newFileName)
            bobbinFileMap.put(level, bobbinFile)
            bobbinFileThreadLocalMap.set(bobbinFileMap)
            return bobbinFile
        } else {
            if (!bobbinFileMap.containsKey(level)) {
                BobbinFile bobbinFile = initFile(newFileName)
                bobbinFileMap.put(level, bobbinFile)
                return bobbinFile
            } else {
                BobbinFile bobbinFile = bobbinFileMap.get(level)
                if (bobbinFile.fileName != newFileName) {
                    bobbinFile.writer.close()
                    bobbinFile = initFile(newFileName)
                    bobbinFileMap.put(level, bobbinFile)
                }
                return bobbinFile
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
