FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src/ ./src/
RUN mvn install


FROM openjdk:17-jdk-slim
WORKDIR /docker_jext
COPY --from=build /app/target/Limited-jEXT-1.0-SNAPSHOT-jar-with-dependencies.jar /docker_jext/
COPY .env /docker_jext/
ENV TZ=Europe/Kyiv
CMD ["java", "-jar", "Limited-jEXT-1.0-SNAPSHOT-jar-with-dependencies.jar"]