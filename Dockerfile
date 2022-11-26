# adapted from https://ktor.io/docs/docker.html#prepare-docker
FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
RUN apt-get update && apt-get install -y \
	libreoffice && \
	apt-get clean
COPY --from=build /home/gradle/src/build/libs/converter.jar /app/converter.jar
ENTRYPOINT ["java","-jar","/app/converter.jar"]