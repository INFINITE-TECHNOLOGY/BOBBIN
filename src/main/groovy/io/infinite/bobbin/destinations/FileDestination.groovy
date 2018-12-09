package io.infinite.bobbin.destinations

import io.infinite.bobbin.Event

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class FileDestination extends Destination {

    static Map<String, File> fileMap = new HashMap<>()

    String prepareKey() {
        return scriptEngine.eval(destinationConfig.properties.get("fileKey") ?: "\"default\"")
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
                Thread.start({
                    zipAndDelete(file)
                })
                file = initFile(newFileName, fileKey)
            }
        }
        return file
    }

    File initFile(String fileName, String fileKey) {
        File file = new File(fileName)
        if (file.exists()) {
            file.zipFileName = prepareCleanupZipFileName(fileName)
            zipAndDelete(file)
            file = new File(fileName)
        }
        file.zipFileName = prepareZipFileName()
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
        println("Bobbin: application working dir: " + new File("./").getCanonicalPath())
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
