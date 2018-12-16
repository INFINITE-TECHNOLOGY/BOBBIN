package io.infinite.bobbin.factories

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.BobbinConfig
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

abstract class BobbinFactoryResourceSystem extends BobbinFactoryFile {

    @Override
    BobbinConfig getBobbinConf() {
        Util.report("Searching for Bobbin config in application resource files using System classloader.")
        URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() {
            URL run() {
                return getResource()
            }
        })
        if (url != null) {
            Util.report("Found.")
            return new ObjectMapper().readValue(new File(url.toURI()).getText(), BobbinConfig.class)
        } else {
            Util.report("Not found.")
            return super.getBobbinConf()
        }
    }

    URL getResource() {
        return ClassLoader.getSystemResource(getConfName())
    }

}
