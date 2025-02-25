FROM openjdk:21-slim

WORKDIR /data/www

COPY clog-api/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
