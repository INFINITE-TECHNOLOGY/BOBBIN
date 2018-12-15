# Infinite Technology ∞ Bobbin

|Type|Version|Branch|Build|Test Coverage|Documentation Coverage|Code Quality|Bintray|
|----|-------|------|-----|-------------|----------------------|------------|-------|
|Unstable|2.0.0-ALPHA|[master](https://github.com/INFINITE-TECHNOLOGY/BOBBIN)|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=master)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|TBD|TBD|TBD|TBD|
|Stable|1.0.1|[BOBBIN_1_0_X](https://github.com/INFINITE-TECHNOLOGY/BOBBIN/tree/BOBBIN_1_0_X)|[![Build Status](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN.svg?branch=BOBBIN_1_0_X)](https://travis-ci.com/INFINITE-TECHNOLOGY/BOBBIN)|[![codecov](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_1_0_X/graphs/badge.svg)](https://codecov.io/gh/INFINITE-TECHNOLOGY/BOBBIN/branch/BOBBIN_1_0_X/graphs)|TBD|TBD|[ ![Download](https://api.bintray.com/packages/infinite-technology/m2/bobbin/images/download.svg) ](https://bintray.com/infinite-technology/m2/bobbin/_latestVersion)|

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
