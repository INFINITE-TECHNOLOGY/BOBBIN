# Infinite Technology ∞ Bobbin 📼

> ***...The Bobbin revolves infinitely...*** \
***...A revolution in Java logging...***

If you haven't been using `Bobbin`, you have not been using any logger at all.

`Bobbin` opens a whole new dimension of insight into the depths of software runtime.

`Bobbin` is the ultimate, definitive and absolute logging solution. Yet the most compact and simple.

|Attribute\Release type|Latest|Stable|
|----------------------|------|------|
|Version|4.0.0-SNAPSHOT|3.0.x|
|Branch|[master](https://github.com/INFINITE-TECHNOLOGY/BOBBIN)|[BOBBIN_3_0_X](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/tree/BOBBIN_3_0_X)|
|CI Build status|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=master)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=BOBBIN_3_0_X)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|
|Test coverage|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs)|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_3_0_X/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_3_0_X/graphs)|
|Library (Maven)|[oss.jfrog.org snapshot](https://oss.jfrog.org/artifactory/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/io/infinite/bobbin/4.0.0-SNAPSHOT)|[ ![Download](https://api.bintray.com/packages/infinite-technology/io.i-t/bobbin/images/download.svg) ](https://bintray.com/infinite-technology/io.i-t/bobbin/_latestVersion)|

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
            <version>3.0.0</version>
        </dependency>
```

## Gradle

```groovy
dependencies {
    compile "org.codehaus.groovy:groovy-all:2.5.4"
    compile "io.i-t:bobbin:3.0.0"
}
```

## Try it now!

Just simply run the below code in Groovy (2.5.4+) console:

```groovy
@Grab('io.i-t:bobbin:3.0.0')
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

Bobbin.yml

```yaml
destinations:
  - name: io.infinite.bobbin.config.ConsoleDestinationConfig
    levels: [warn, error, info]
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [io.infinite]
    fileName: ("./LOGS/INFINITE/${className}/${level}/${className}_${level}_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    fileName: ("./LOGS/PACKAGES/${className}/${level}/${className}_${level}_${date}.log")
    format: dateTime + '|' + level + '|' + threadName + '|' + className + '|' + message + '\n'
```