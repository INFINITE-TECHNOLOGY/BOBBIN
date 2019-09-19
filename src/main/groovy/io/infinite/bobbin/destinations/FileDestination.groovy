package io.infinite.bobbin.destinations

import groovy.time.TimeCategory
import groovy.transform.CompileDynamic
import io.infinite.bobbin.BobbinFile
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.helpers.Util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

class FileDestination extends Destination {

    static ThreadLocal<Map<String, BobbinFile>> bobbinFileThreadLocalCache = new ThreadLocal<Map<String, BobbinFile>>()

    static ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<String, ReentrantLock>(8, 0.9f, 1)

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    FileDestination(DestinationConfig destinationConfig) {
        super(destinationConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    protected void store(String finalOutputMessageText, Level level, String className, String date) {
        String newFileName = bobbinScriptEngine.evalFileName(level.value(), className, date)
        BobbinFile bobbinFile = refreshCurrentFile(newFileName)
        ReentrantLock newLock = new ReentrantLock()
        ReentrantLock lock = lockMap.putIfAbsent(bobbinFile.getCanonicalPath(), newLock) ?: newLock
        try {
            lock.lock()
            bobbinFile.writer.write(finalOutputMessageText)
            bobbinFile.writer.flush()
        } finally {
            lock.unlock()
        }
    }

    @CompileDynamic
    void cleanupCache(Map<String, BobbinFile> bobbinFileMap) {
        Date checkDate
        use (TimeCategory) {
            checkDate = new Date() - 24.hours
        }
        bobbinFileMap.each {
            if (it.value.createDate.before(checkDate)) {
                it.value.writer.close()
            }
        }
        bobbinFileMap.removeAll {
            it.value.createDate.before(checkDate)
        }
    }

    BobbinFile refreshCurrentFile(String newFileName) {
        Map<String, BobbinFile> bobbinFileMap = bobbinFileThreadLocalCache.get()
        if (bobbinFileMap == null) {
            bobbinFileMap = new HashMap<String, BobbinFile>()
            BobbinFile bobbinFile = initFile(newFileName)
            bobbinFileMap.put(newFileName, bobbinFile)
            bobbinFileThreadLocalCache.set(bobbinFileMap)
            return bobbinFile
        } else {
            if (!bobbinFileMap.containsKey(newFileName)) {
                cleanupCache(bobbinFileMap)
                BobbinFile bobbinFile = initFile(newFileName)
                bobbinFileMap.put(newFileName, bobbinFile)
                return bobbinFile
            } else {
                BobbinFile bobbinFile = bobbinFileMap.get(newFileName)
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
