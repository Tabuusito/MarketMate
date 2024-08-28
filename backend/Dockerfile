FROM maven:3.9.5 AS build

# Directorio donde se alojará la aplicación
WORKDIR /app

COPY demo /app

RUN mvn clean install
#RUN mvn -f /app/pom.xml clean package

# Expone el puerto donde corre Spring Boot
EXPOSE 8080

#FROM openjdk:17-slim

#COPY --from=build /app/target/marketmate-backend.jar /app

ENTRYPOINT ["mvn","spring-boot:run"]
#ENTRYPOINT ["java","-jar","/app/marketmate-backend.jar"]
