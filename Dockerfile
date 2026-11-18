FROM amazoncorretto:17

WORKDIR /app

COPY target/population-reporting-system-1.2.0-jar-with-dependencies.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
