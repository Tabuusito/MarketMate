# Usa una imagen base de OpenJDK 17
FROM openjdk:17-slim

# Directorio donde se alojará la aplicación
WORKDIR /app

# Copia el archivo jar del proyecto Spring Boot
COPY ./demo/target/demo-0.0.1-SNAPSHOT.jar /app

# Expone el puerto donde corre Spring Boot
EXPOSE 8080

# Comando para correr la aplicación
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
