# Infinite Technology ∞ Bobbin

[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=BOBBIN_1_0_X)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)
[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_1_0_X/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_1_0_X/graphs)
[ ![Download](https://api.bintray.com/packages/infinite-technology/m2/bobbin/images/download.svg) ](https://bintray.com/infinite-technology/m2/bobbin/_latestVersion)

This branch corresponds to major version 1.x.x.

Bobbin is a Groovy Slf4j-compatible logger designed for multi-threaded applications.

Bobbin leverages the concept of Logback sifting appender while providing much more easier configuration.

References:
* [Bobbin Documentation](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/wiki)

**Sample configuration:**

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
