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
        refreshCurrentFile(level, newFileName)
        bobbinFileThreadLocalMap.get().get(level).writer.write(finalOutputMessageText)
        bobbinFileThreadLocalMap.get().get(level).writer.flush()
    }

    void refreshCurrentFile(Level level, String newFileName) {
        if (bobbinFileThreadLocalMap.get() == null) {
            Map<Level, BobbinFile> bobbinFileMap = new HashMap<Level, BobbinFile>()
            bobbinFileMap.put(level, initFile(newFileName))
            bobbinFileThreadLocalMap.set(bobbinFileMap)
        } else {
            if (!bobbinFileThreadLocalMap.get().containsKey(level)) {
                bobbinFileThreadLocalMap.get().put(level, initFile(newFileName))
            } else {
                if (bobbinFileThreadLocalMap.get().get(level).fileName != newFileName) {
                    bobbinFileThreadLocalMap.get().get(level).writer.close()
                    bobbinFileThreadLocalMap.get().put(level, initFile(newFileName))
                }
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
