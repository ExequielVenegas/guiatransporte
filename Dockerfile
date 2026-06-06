FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

# Carpeta donde se montará el EFS
RUN mkdir -p /app/efs

COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources/wallet ./wallet

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]