# integrated-twitter ![master](https://github.com/mebr0/integrated-twitter/actions/workflows/master.yml/badge.svg)

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

Docker

- [x] Dockerfile for JVM mode

Tests

- [x] Integration tests
- [x] Coverage reports
- [x] Sonarqube reports

## Commands

### Dev mode

`./gradlew quarkusDev` - live coding

### JVM mode

`./gradlew test` - run integration tests and generate coverage reports

`./gradlew sonarqube` - send coverage reports to sonarqube 
(define `sonar.login` and `sonar.organization` properties)

`./gradlew build` - build project

`./gradlew build -Dquarkus.package.type=uber-jar` - build project with uber jar

`java -jar build/quarkus-app/quarkus-run.jar` - run artifact

### Native mode

`./gradlew build -Dquarkus.package.type=native` - build project to native executable

`./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true` - 
build project to native executable (without GraalVM)

`./build/integrated-twitter-1.0-SNAPSHOT-runner` - run artifact

### Docker

Use Dockerfile in root of project for containerization of app

[Social network]: https://jsonplaceholder.typicode.com
