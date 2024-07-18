FROM eclipse-temurin:17-jdk-alpine as prod
COPY *.jar app.jar
ENV SERVER_PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]