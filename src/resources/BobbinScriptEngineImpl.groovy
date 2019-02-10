import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinScriptEngine

@CompileStatic
class BobbinScriptEngineImpl implements BobbinScriptEngine {

    Boolean isLevelEnabled(Level level) {
        $levelScript
    }

    Boolean isClassEnabled(String className) {
        $classesScript
    }

    String formatMessage(Event event) {
        $formatScript
    }


    String evalFileName(String) {
        $fileNameScript
    }

}