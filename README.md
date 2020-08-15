## Introduction

`Bobbin` is a high-performance `Slf4j`-compatible logger designed for multi-threaded applications (especially those with persistent threads like batch, stream and messaging applications).

`Bobbin` leverages the concept of `Logback`/`Log4j2` sifting appenders while providing much more easier configuration using native `Groovy`/`Java` scripting expressions.

> Bobbin is available in `JCenter` repository.

## Maven

> ❗ Note that there is no `<type>pom</type>`

> [Example Maven project with Bobbin](https://github.com/INFINITE-TECHNOLOGY/BOBBIN_MAVEN_EXAMPLE)

```xml
...
<repositories>
    <repository>
        <id>jcenter</id>
        <name>jcenter</name>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>
...
<dependency>
    <groupId>io.i-t</groupId>
    <artifactId>bobbin</artifactId>
    <version>4.1.0</version>
</dependency>
...
```

## Gradle

```groovy
repositories {
    jcenter()
}
dependencies {
    compile "org.codehaus.groovy:groovy-all:2.5.4"
    compile "io.i-t:bobbin:4.1.0"
}
```

## Sample configurations

Examples of `Bobbin.yml` for different use cases.

#### General usage

```yaml
destinations:
  - name: io.infinite.bobbin.config.ConsoleDestinationConfig
    levels: [warn, error, info]
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [io.infinite]
    fileName: ("./LOGS/INFINITE/${className}/${level}/${className}_${level}_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    fileName: ("./LOGS/PACKAGES/${className}/${level}/${className}_${level}_${date}.log")
    format: dateTime + delimiter + level + delimiter + threadName + delimiter + className + delimiter + message
```

#### Spring Boot

> [Logging Spring Boot HTTP with Bobbin](https://i-t.io/website/#/Blog/LoggingSpringBoot)

```yaml
destinations:
  - name: io.infinite.bobbin.config.ConsoleDestinationConfig
    formatThrowable: "%format% + delimiter + throwable"
    levels: [warn, error, info]
  - name: io.infinite.bobbin.config.FileDestinationConfig
    levels: [warn]
    fileName: ("./LOGS/WARNINGS_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    levels: [error]
    fileName: ("./LOGS/ERRORS_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [org.springframework.web]
    fileName: ("./LOGS/SPRING_WEB/SPRING_WEB_${date}.log")
```

#### Logstash

Pass named parameters to `Logstash` without using `MDC` (using positioned logging arguments indexes instead).

```yaml
levels: [info, warn, error, debug]
destinations:
  - name: io.infinite.bobbin.config.ConsoleDestinationConfig
    packages: [com.acme.logstash]
    dateTimeFormat: "yyyy-MM-dd'T'HH:mm:ss:SSSZ"
    lineBreak: ",\r\n"
    formatArgs: |-
      """{
        "@timestamp": "$dateTime",
        "@version": "1",
        "message": "$message",
        "logger_name": "$className",
        "thread_name": "$threadName",
        "level": "$level",
        "username": "${args[0]}",
        "URI": "${args[1]}",
        "sessionId": "${args[2]}",
        "transactionId": "${args[3]}",
        "instanceId": "${MDC.get('instanceUUID')}"
      }"""
```

#### MDC and log formatting

Use `MDC` and change formatting of individual logging signatures - like `formatThrowable` in this case.

`%format%` placeholder helps to reuse the base formatting (which itself can be also redefined using `format` parameter).

```yaml
format: dateTime + delimiter + MDC.get("instanceUUID") + delimiter + level + delimiter + threadName + delimiter + className + delimiter + message
destinations:
  - name: io.infinite.bobbin.config.ConsoleDestinationConfig
    formatThrowable: "%format% + delimiter + throwable"
    levels: [warn, error, info]
  - name: io.infinite.bobbin.config.FileDestinationConfig
    levels: [warn]
    fileName: ("./LOGS/WARNINGS_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    levels: [error]
    fileName: ("./LOGS/ERRORS_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [org.springframework.web]
    fileName: ("./LOGS/SPRING_WEB/SPRING_WEB_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [io.infinite]
    fileName: ("./LOGS/THREADS/${threadGroupName}/${threadName}/${threadName}_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [conf.plugins.output]
    fileName: ("./LOGS/PLUGINS/OUTPUT/${className}/${className}_${date}.log")
  - name: io.infinite.bobbin.config.FileDestinationConfig
    packages: [conf.plugins.input]
    fileName: ("./LOGS/PLUGINS/INPUT/${className}/${className}_${date}.log")
```