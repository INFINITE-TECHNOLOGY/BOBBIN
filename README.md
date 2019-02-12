# Infinite Technology ∞ Bobbin 📼

The Bobbin revolves infinitely.\
A revolution in Java logging.

|Attribute\Release type|Latest|Stable|
|----------------------|------|------|
|Version|2.0.0-SNAPSHOT|N/A|
|Branch|[master](https://github.com/INFINITE-TECHNOLOGY/BOBBIN)|N/A|
|CI Build status|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=master)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|N/A|
|Test coverage|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs)|N/A|
|Library (Maven)|[oss.jfrog.org snapshot](https://oss.jfrog.org/artifactory/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/io/infinite/bobbin/2.0.0-SNAPSHOT)|N/A|

Bobbin is a high-performance Groovy Slf4j-compatible logger designed for multi-threaded applications (especially those with persistent threads like batch and messaging applications).

Bobbin leverages the concept of Logback/Log4j2 sifting appenders while providing much more easier configuration using native Groovy/Java scripting expressions.

References:
* [Bobbin Documentation](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/wiki)


**Sample configuration:**

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
