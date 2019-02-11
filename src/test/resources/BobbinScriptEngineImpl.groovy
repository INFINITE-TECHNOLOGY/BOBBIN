import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinScriptEngine

@CompileStatic
class BobbinScriptEngineImpl extends BobbinScriptEngine {

    BobbinScriptEngineImpl(String code) {
        super(code)
    }

    Boolean isLevelEnabled(String level) {
        $levelScript
    }

    Boolean isClassEnabled(String className) {
        $classesScript
    }

    String evalFileName(String level, String className, String date) {
        $fileNameScript
    }

    String formatLine(String level, String className, String date, String message) {
        $formatScript
    }

    String formatLine(String level, String className, String date, String message, Object arg) {
        $argFormatScript
    }

    String formatLineWithArray(String level, String className, String date, String message, Object... args) {
        $argsFormatScript
    }

    String formatLine(String level, String className, String date, String message, Object arg1, Object arg2) {
        $arg1arg2FormatScript
    }

    String formatLine(String level, String className, String date, String message, Throwable throwable) {
        $throwableFormatScript
    }

    String getDateFormat() {
        "$dateFormat"
    }

    String getDateTimeFormat() {
        "$dateTimeFormat"
    }

}