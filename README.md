# integrated-twitter

Service for social networks without own data, **INTEGRATION ONLY**.

## External services

1. [Social network] - main source of data

## Check list

REST API

- [ ] JSON and/or XML endpoints
- [ ] Openapi & Swagger
- [ ] Validation

Data

- [ ] Transformations

HTTP

- [ ] HTTP Requests
- [ ] HTTP Statuses (errors)

## Commands

### Dev mode

`./gradlew quarkusDev` - live coding

### JVM mode

`./gradlew build` - build project

`./gradlew build -Dquarkus.package.type=uber-jar` - build project with uber jar

`java -jar build/quarkus-app/quarkus-run.jar` - run artifact

### Native mode

`./gradlew build -Dquarkus.package.type=native` - build project to native executable

`./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true` - 
build project to native executable (without GraalVM)

`./build/integrated-twitter-1.0-SNAPSHOT-runner` - run artifact

[Social network]: https://jsonplaceholder.typicode.com
