FROM openjdk:8-jdk-alpine

ARG JAR_FILE

COPY ./target/${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "-Duser.timezone=America/Sao_Paulo", "/app.jar"]
