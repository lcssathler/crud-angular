# FROM maven:3.8.3-openjdk-17 AS build
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:17.0.1-jdk-slim
# COPY --from=build /target/crud-angular-0.0.1-SNAPSHOT.jar crud-angular.jar
# EXPOSE 8080
# ENTRYPOINT [ "java","-jar","crud-angular.jar" ]

# Use a imagem oficial do OpenJDK 17
FROM openjdk:17-jdk-alpine

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo pom.xml e faça o download das dependências para aproveitar o cache do Docker
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# Copie o código-fonte para o contêiner
COPY src ./src

# Compile o projeto
RUN ./mvnw clean package -DskipTests

# O arquivo JAR gerado estará em target/
ARG JAR_FILE=target/*.jar

# Copie o arquivo JAR para o contêiner com o nome "app.jar"
COPY ${JAR_FILE} app.jar

# Porta que será exposta pelo contêiner
EXPOSE 8080

# Comando para executar o aplicativo quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "app.jar"]
