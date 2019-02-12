package io.infinite.bobbin.config

import groovy.transform.CompileStatic
import io.infinite.bobbin.BobbinScriptEngineFactory

@CompileStatic
class BobbinConfig extends AbstractConfig {

    String levels = "true"
    String classes = "true"
    String dateFormat = "yyyy-MM-dd"
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss:SSS"
    String format = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '\\n'"
    String formatThrowable = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + new io.infinite.supplies.ast.exceptions.ExceptionUtils().stacktrace(throwable) + '\\n'"
    String formatArg = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + arg.toString() + '\\n'"
    String formatArgs = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + args.toString() + '\\n'"
    String formatArg1Arg2 = "dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '|' + arg1.toString() + ';' + arg2.toString() + '\\n'"
    List<DestinationConfig> destinations = [new DestinationConfig()]

    void setDestinations(List<DestinationConfig> destinations) {
        this.destinations = destinations
        setBobbinConfig()
    }

    void setBobbinConfig() {
        destinations.each {it.setBobbinConfig(this)}
    }

    {
        setBobbinConfig()
    }

}
