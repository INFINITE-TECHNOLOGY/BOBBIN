package io.infinite.bobbin.destinations


import io.infinite.bobbin.BobbinFile
import io.infinite.bobbin.Level
import io.infinite.bobbin.config.FileDestinationConfig
import org.slf4j.helpers.Util

import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

class FileDestination extends Destination {

    static ThreadLocal<Map<String, BobbinFile>> bobbinFileThreadLocalCache = new ThreadLocal<Map<String, BobbinFile>>()

    static ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<String, ReentrantLock>(8, 0.9f, 1)

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    FileDestination(FileDestinationConfig destinationConfig) {
        super(destinationConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    @Override
    protected void store(String finalOutputMessageText, String loggerName, Level level, String date) {
        String newFileName = bobbinEngine.evalFileName(level.value(), loggerName, date)
        BobbinFile bobbinFile = refreshCurrentFile(newFileName)
        ReentrantLock newLock = new ReentrantLock()
        ReentrantLock lock = lockMap.putIfAbsent(bobbinFile.getCanonicalPath(), newLock) ?: newLock
        try {
            lock.lock()
            bobbinFile.writer.write(finalOutputMessageText + destinationConfig.lineBreak)
            bobbinFile.writer.flush()
        } finally {
            lock.unlock()
        }
    }

    void cleanupCache(Map<String, BobbinFile> bobbinFileMap) {
        Instant checkDate = Instant.now() - Duration.ofHours(24)
        bobbinFileMap.each {
            if (it.value.createDate.isBefore(checkDate)) {
                it.value.writer.close()
            }
        }
        bobbinFileMap.removeAll {
            it.value.createDate.isBefore(checkDate)
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
