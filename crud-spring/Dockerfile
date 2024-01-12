FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/crud-angular-0.0.1-SNAPSHOT.jar crud-angular.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","crud-angular.jar" ]

