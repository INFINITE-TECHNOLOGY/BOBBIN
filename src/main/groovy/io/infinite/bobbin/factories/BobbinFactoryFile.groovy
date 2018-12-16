package io.infinite.bobbin.factories

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.BobbinConfig
import org.slf4j.helpers.Util

class BobbinFactoryFile extends BobbinFactoryBase {

    @Override
    BobbinConfig getBobbinConf() {
        Util.report("Searching for Bobbin config in: " + getConfPath() + " (full path: ${new File(getConfPath()).getCanonicalPath()})")
        File file = new File(getConfPath())
        if (file.exists()) {
            Util.report("Found")
            return new ObjectMapper().readValue(file.getText(), BobbinConfig.class)
        } else {
            Util.report("Not found.")
            return super.getBobbinConf()
        }
    }

    String getConfDir() {
        return "./"
    }

    String getConfName() {
        return "Bobbin.json"
    }

    String getConfPath() {
        return getConfDir() + getConfName()
    }

}
