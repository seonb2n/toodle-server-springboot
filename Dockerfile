FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FIULE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]