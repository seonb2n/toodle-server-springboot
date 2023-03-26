FROM openjdk:17.0.2-jdk
ARG JAR_FILE_PATH=build/libs/toodle_server_springboot-v1.0.jar
COPY $JAR_FILE_PATH toodle_server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "/toodle_server.jar"]