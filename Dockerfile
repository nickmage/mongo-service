FROM gradle:7.3.3-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN gradle clean build --no-daemon

FROM openjdk:17-alpine

RUN mkdir /app

COPY --from=build /home/app/build/resources /app/resources
COPY --from=build /home/app/build/libs/mongo-service-0.0.1-SNAPSHOT.jar /app/application.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]