package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import org.slf4j.helpers.Util

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class FileDestination extends Destination {

    Map<String, File> fileMap = new HashMap<>()

    FileDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

    String prepareKey() {
        return scriptEngine.eval(destinationConfig.properties.get("fileKey") ?: "\"default\"")
    }

    String prepareFileName() {
        return scriptEngine.eval(destinationConfig.properties.get("fileName"))
    }

    String prepareZipFileName(String origFileName) {
        scriptEngine.put("origFileName", origFileName)
        return scriptEngine.eval(destinationConfig.properties.get("zipFileName"))
    }

    String prepareCleanupZipFileName(String origFileName) {
        scriptEngine.put("origFileName", origFileName)
        return scriptEngine.eval(destinationConfig.properties.get("cleanupZipFileName"))
    }

    @Override
    protected void store(Event event) {
        String key = prepareKey()
        String newFileName = prepareFileName()
        File file = getFile(newFileName, key)
        file.writer.write(event.getFormattedMessage())
        file.writer.flush()
    }

    File getFile(String newFileName, String fileKey) {
        File file
        if (!fileMap.containsKey(fileKey)) {
            file = initFile(newFileName, fileKey)
        } else {
            file = fileMap.get(fileKey)
            if (file.fileName != newFileName) {
                Util.report("Bobbin: $newFileName replaces ${file.fileName} which is going to be archived. Thread name is ${Thread.currentThread().getName()}, thread group is ${Thread.currentThread().getThreadGroup().getName()}, key is ${fileKey}")
                File fileToZip = file //avoid reassigning variable outside of thread closure
                Thread.start({
                    zipAndDelete(fileToZip)
                })
                file = initFile(newFileName, fileKey)
            }
        }
        return file
    }

    File initFile(String fileName, String fileKey) {
        Util.report("Bobbin: Initializing ${fileName}. Thread name is ${Thread.currentThread().getName()}, thread group is ${Thread.currentThread().getThreadGroup().getName()}, key is ${fileKey}")
        File file = new File(fileName)
        file.zipFileName = prepareZipFileName(fileName)
        file.fileName = fileName
        file.getParentFile().mkdirs()
        file.writer = new FileWriter(file, true)
        fileMap.put(fileKey, file)
        return file
    }

    static {
        File.getMetaClass().fileName = null
        File.getMetaClass().zipFileName = null
        File.getMetaClass().writer = null
        Util.report("Bobbin: " + Thread.currentThread().getName().padRight(16) + ": " + "application working dir: " + new File("./").getCanonicalPath())
    }

    void zipAndDelete(File file) {
        final Integer BUFFER_LENGTH = 2048
        if (file.isFile()) {
            new File(file.zipFileName as String).getParentFile().mkdirs()
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
