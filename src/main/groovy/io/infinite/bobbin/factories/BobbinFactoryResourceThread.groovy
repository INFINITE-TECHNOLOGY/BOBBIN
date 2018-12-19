package io.infinite.bobbin.factories

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.BobbinConfig
import org.slf4j.helpers.Util

import java.security.AccessController
import java.security.PrivilegedAction

abstract class BobbinFactoryResourceThread extends BobbinFactoryResourceSystem {

    @Override
    BobbinConfig getBobbinConf() {
        report("Searching for Bobbin config in application resource files using Thread classloader.")
        URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() {
            URL run() {
                return getResource()
            }
        })
        if (url != null) {
            report("Found: " + new File(url.toURI()).getCanonicalPath())
            return new ObjectMapper().readValue(new File(url.toURI()).getText(), BobbinConfig.class)
        } else {
            report("Not found.")
            return super.getBobbinConf()
        }
    }

    URL getResource() {
        report("(Resource name: ${getConfName()})")
        ClassLoader classLoader = getClassLoader()
        if (classLoader != null) {
            return classLoader.getResource(getConfName())
        } else {
            return super.getResource()
        }
    }

    ClassLoader getClassLoader() {
        report("Using thread classloader")
        return Thread.currentThread().getContextClassLoader()
    }

}
