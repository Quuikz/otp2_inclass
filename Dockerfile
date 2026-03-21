FROM maven:latest

LABEL authors="riku"

WORKDIR .

COPY . .

RUN mvn -f pom.xml clean package -DskipTests

CMD ["java","-jar","./target/Localization-1.0-SNAPSHOT.jar"]