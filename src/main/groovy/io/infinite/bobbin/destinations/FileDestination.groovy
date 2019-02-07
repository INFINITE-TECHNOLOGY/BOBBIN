package io.infinite.bobbin.destinations

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinFile
import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.helpers.Util

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@CompileStatic
class FileDestination extends Destination {

    Map<String, BobbinFile> fileMap = new HashMap<>()

    ///////////////////CONSTRUCTOR \/\/\/\/\/\/
    FileDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }
    ///////////////////CONSTRUCTOR /\/\/\/\/\/\

    String prepareZipFileName(String origFileName) {
        scriptEngine.put("origFileName", origFileName)
        return scriptEngine.eval(destinationConfig.properties.get("zipFileName"))
    }

    @Override
    protected void store(Event event) {
        String key = scriptEngine.eval(destinationConfig.properties.get("fileKey") ?: "\"default\"")
        String newFileName = scriptEngine.eval(destinationConfig.properties.get("fileName"))
        BobbinFile file = getFile(newFileName, key)
        file.writer.write(event.getFormattedMessage())
        file.writer.flush()
    }

    BobbinFile getFile(String newFileName, String fileKey) {
        BobbinFile file
        if (!fileMap.containsKey(fileKey)) {
            file = initFile(newFileName, fileKey)
        } else {
            file = fileMap.get(fileKey)
            if (file.fileName != newFileName) {
                BobbinFile fileToZip = file //avoid reassigning variable outside of thread closure
                Thread.start({
                    zipAndDelete(fileToZip)
                })
                file = initFile(newFileName, fileKey)
            }
        }
        return file
    }

    BobbinFile initFile(String fileName, String fileKey) {
        BobbinFile file = new BobbinFile(fileName)
        file.zipFileName = prepareZipFileName(fileName)
        file.fileName = fileName
        file.getParentFile().mkdirs()
        file.writer = new FileWriter(file, true)
        fileMap.put(fileKey, file)
        return file
    }

    static {
        Util.report("Bobbin: " + Thread.currentThread().getName().padRight(16) + ": " + "application working dir: " + new BobbinFile("./").getCanonicalPath())
    }

    void zipAndDelete(BobbinFile file) {
        final Integer BUFFER_LENGTH = 2048
        if (file.isFile()) {
            new BobbinFile(file.zipFileName as String).getParentFile().mkdirs()
            FileOutputStream fileOutputStream = new FileOutputStream(file.zipFileName as String)
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream))
            byte[] bytes = new byte[BUFFER_LENGTH]
            FileInputStream fileInputStream = new FileInputStream(file)
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER_LENGTH)
            ZipEntry entry = new ZipEntry(file.getName())
            zipOutputStream.putNextEntry(entry)
            Integer countBytes
            while ((countBytes = bufferedInputStream.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                zipOutputStream.write(bytes, 0, countBytes)
            }
            bufferedInputStream.close()
            zipOutputStream.close()
            file.writer?.close()
            file.delete()
        }
    }

}
