FROM eclipse-temurin:21-jdk
LABEL authors="riku"

WORKDIR .

COPY . .

RUN mvn clean package -DskipTests

CMD["java","-jar", "/target/Localization-1.0-SNAPSHOT.jar"]