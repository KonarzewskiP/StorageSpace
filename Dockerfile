FROM openjdk:17
EXPOSE 8080
COPY target/storage-space-app.jar app.jar
ENTRYPOINT ["java","-Duser.timezone=UTC", "-jar", "app.jar"]