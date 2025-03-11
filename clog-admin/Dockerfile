FROM openjdk:21-slim

WORKDIR /data/www

COPY clog-admin/build/libs/*.jar admin.jar

ENTRYPOINT ["java", "-jar", "admin.jar"]
