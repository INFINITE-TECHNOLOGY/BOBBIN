package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event
import io.infinite.speedometer.Speedometer

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Speedometer
class FileDestination extends Destination {

    static Map<String, File> fileMap = new HashMap<>()

    String prepareKey() {
        return destinationConfig.properties.get("fileKey") ?: "\"default\""
    }

    String prepareFileName() {
        return scriptEngine.eval(destinationConfig.properties.get("fileName"))
    }

    String prepareZipFileName() {
        return scriptEngine.eval(destinationConfig.properties.get("zipFileName"))
    }

    String prepareCleanupZipFileName(String origFileName) {
        scriptEngine.put("origFileName", origFileName)
        return scriptEngine.eval(destinationConfig.properties.get("cleanupZipFileName"))
    }

    @Override
    protected void store(Event event) {
        String key = prepareKey()
        File file
        String newFileName = prepareFileName()
        if (!fileMap.containsKey(key)) {
            file = initFile(newFileName)
        } else {
            file = fileMap.get(key)
            if (file.fileName != newFileName) {
                Thread.start({
                    zipAndDelete(file)
                })
                file = initFile(newFileName)
            }
        }
        fileMap.put(key, file)
        file.writer.append(event.getFormattedMessage())
    }

    File initFile(String fileName) {
        File file = new File(fileName)
        if (file.exists()) {
            file.zipFileName = prepareCleanupZipFileName(fileName)
            zipAndDelete(file)
            file = new File(fileName)
        }
        file.zipFileName = prepareZipFileName()
        file.fileName = fileName
        file.writer = new FileWriter(file, true)
        file.getParentFile().mkdirs()
        println("Bobbin log: " + file.getCanonicalPath())
        return file
    }

    static {
        File.getMetaClass().fileName = null
        File.getMetaClass().zipFileName = null
        File.getMetaClass().writer = null
    }

    static void zipAndDelete(File file) {
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
            file.delete()
        }
    }

}
