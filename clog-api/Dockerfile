FROM openjdk:21-slim

WORKDIR /data/www

COPY clog-api/build/libs/*.jar app.jar
COPY libs/elastic-apm-agent-*.jar elastic-apm-agent.jar

ENTRYPOINT [ \
    "java", \
    "-javaagent:elastic-apm-agent.jar", \
    "-Delastic.apm.service_name=clog-api", \
    "-Delastic.apm.application_packages=org.depromeet.clog.server.api", \
    "-Delastic.apm.server_url=http://apm-server:8200", \
    "-jar", \
    "app.jar"]
