# Infinite Technology ∞ Bobbin 📼

> ***...The Bobbin revolves infinitely...*** \
***...A revolution in Java logging...***

|Attribute\Release type|Latest|Stable|
|----------------------|------|------|
|Version|3.0.0-SNAPSHOT|2.0.x|
|Branch|[master](https://github.com/INFINITE-TECHNOLOGY/BOBBIN)|[BOBBIN_2_0_X](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/tree/BOBBIN_2_0_X)|
|CI Build status|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=master)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=BOBBIN_2_0_X)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|
|Test coverage|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs)|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_2_0_X/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_2_0_X/graphs)|
|Library (Maven)|[oss.jfrog.org snapshot](https://oss.jfrog.org/artifactory/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/io/infinite/bobbin/3.0.0-SNAPSHOT)|[ ![Download](https://api.bintray.com/packages/infinite-technology/m2/bobbin/images/download.svg) ](https://bintray.com/infinite-technology/m2/bobbin/_latestVersion)|

Bobbin is a high-performance Groovy Slf4j-compatible logger designed for multi-threaded applications (especially those with persistent threads like batch and messaging applications).

Bobbin leverages the concept of Logback/Log4j2 sifting appenders while providing much more easier configuration using native Groovy/Java scripting expressions.

> ❇ Bobbin is available both in `JCenter` and `Maven Central` repositories.

## Maven

> ❗ Note that there is no `<type>pom</type>`

> ❇ [Example Maven project with Bobbin](https://github.com/INFINITE-TECHNOLOGY/BOBBIN_MAVEN_EXAMPLE)

```xml
        <dependency>
            <groupId>io.i-t</groupId>
            <artifactId>bobbin</artifactId>
            <version>2.0.10</version>
        </dependency>
```

## Gradle

```groovy
dependencies {
    compile "org.codehaus.groovy:groovy-all:2.5.4"
    compile "io.i-t:bobbin:2.0.10"
}
```

## Try it now!

Just simply run the below code in Groovy (2.5.4+) console:

```groovy
@Grab('io.i-t:bobbin:2.0.10')
@Grab('org.slf4j:slf4j-api:1.7.25')
import groovy.util.logging.Slf4j

@Slf4j
class TryMe {
    
    void tryMe() {
        log.info("Welcome to the revolution in Java Logging.")
    }

}

new TryMe().tryMe()
```

Output:

```
2019-03-15 15:19:14:337|info|Thread-3|TryMe|Welcome to the revolution in Java Logging.
```

## Documentation
* [Bobbin Documentation](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/wiki)


## Sample configuration

Bobbin.json

```json
{
  "levels": "['debug', 'info', 'warn', 'error'].contains(level)",
  "destinations": [
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileName": "\"./LOGS/PLUGINS/INPUT/${className}/${level}/${className}_${level}.log\""
      },
      "classes": "className.contains('conf.plugins.input')"
    },
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileName": "\"./LOGS/PLUGINS/OUTPUT/${className}/${level}/${threadName}_${level}_${date}.log\""
      },
      "classes": "className.contains('conf.plugins.output')"
    },
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileName": "\"./LOGS/THREADS/${threadGroupName}/${threadName}/${level}/${threadName}_${level}_${date}.log\""
      },
      "classes": "className.contains('io.infinite.')"
    },
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileName": "\"./LOGS/ALL/WARNINGS_AND_ERRORS_${date}.log\""
      },
      "levels": "['warn', 'error'].contains(level)"
    },
    {
      "name": "io.infinite.bobbin.destinations.ConsoleDestination",
      "levels": "['warn', 'error'].contains(level)"
    }
  ]
}
```