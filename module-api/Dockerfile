FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/module-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} module-api.jar
ENTRYPOINT ["java","-jar","/module-api.jar"]