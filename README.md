# Infinite Technology ∞ Bobbin 📼

|Attribute\Release type|Latest|Stable|
|----------------------|------|------|
|Version|2.0.0-SNAPSHOT|N/A|
|Branch|[master](https://github.com/INFINITE-TECHNOLOGY/BOBBIN)|N/A|
|CI Build status|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=master)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|N/A|
|Test coverage|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/master/graphs)|N/A|
|Library (Maven)|[oss.jfrog.org snapshot](https://oss.jfrog.org/artifactory/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/io/infinite/bobbin/2.0.0-SNAPSHOT)|N/A|

Bobbin is a Groovy Slf4j-compatible logger designed for multi-threaded applications.

Bobbin leverages the concept of Logback sifting appender while providing much more easier configuration.

References:
* [Bobbin Documentation](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/wiki)

------------------

**IMPORTANT**: "levels" and "classes" scripts in configuration are **cached against message level and class name** respectively.

Documentation updates are pending to outline this.

In 2.x.x there will be added optional "filter" script without caching.

Thus including into "levels" and "classes" any other tokens/runtime variables (like thread name) **will not be evaluated** due to caching of scripts by message level and classname.

The intention of these scripts is to have more flexibility in syntax, but runtime data flexibility is limited due to performance consideration.

------------------

**Sample configuration:**

Bobbin.json

```json
{
  "levels": "['debug', 'info', 'warn', 'error'].contains(level)",
  "classes": "all",
  "destinations": [
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileKey": "threadName + level",
        "fileName": "\"./LOGS/${threadName}/${level}/${threadName}_${level}_${date}.log\"",
        "zipFileName": "\"./LOGS/${threadName}/${level}/ARCHIVE/${threadName}_${level}_${date}.zip\"",
        "cleanupZipFileName": "\"${origFileName}_${System.currentTimeMillis().toString()}.zip\""
      },
      "format": "\"${dateTime}|${level}|${threadName}|${className}|${event.message}\\n\"",
      "classes": "className.contains('io.infinite.')"
    },
    {
      "name": "io.infinite.bobbin.destinations.FileDestination",
      "properties": {
        "fileName": "\"./LOGS/WARNINGS_AND_ERRORS_${date}.log\"",
        "zipFileName": "\"./LOGS/WARNINGS_AND_ERRORS_${date}.zip\"",
        "cleanupZipFileName": "\"${origFileName}_${System.currentTimeMillis().toString()}.zip\""
      },
      "format": "\"${dateTime}|${level}|${threadName}|${className}|${event.message}\\n\"",
      "levels": "['warn', 'error'].contains(level)"
    },
    {
      "name": "io.infinite.bobbin.destinations.ConsoleDestination",
      "format": "\"${dateTime}|${level}|${threadName}|${className}|${event.message}\\n\"",
      "levels": "['warn', 'error'].contains(level)"
    }
  ]
}
```
