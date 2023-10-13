# Etapa de construção
FROM ubuntu:latest AS Build

RUN apt-get update -y && apt-get install openjdk-17-jdk maven -y

#WORKDIR /app
COPY . .

RUN mvn clean install

# Etapa final
FROM openjdk:17-jdk-slim as Final

EXPOSE 8080

COPY --from=build /target/todolist-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
