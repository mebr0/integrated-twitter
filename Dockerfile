####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the container image run:
#
# ./gradlew build
#
# Then, build the image with:
#
# docker build -t mebr0/integrated-twitter .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 mebr0/integrated-twitter
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5005
#
# Then run the container using :
#
# docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" mebr0/integrated-twitter
#
###
FROM openjdk:11-jre-slim

RUN useradd -m -s /bin/bash 1001 \
    && mkdir /app \
    && chown 1001 /app \
    && chmod "g+rwX" /app \
    && chown 1001:root /app

COPY build/quarkus-app/lib/ /app/lib/
COPY build/quarkus-app/*.jar /app/app.jar
COPY build/quarkus-app/app/ /app/app/
COPY build/quarkus-app/quarkus/ /app/quarkus/

WORKDIR /app

EXPOSE 8080
USER 1001

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dquarkus.http.host=0.0.0.0", "-jar", "/app/app.jar"]
