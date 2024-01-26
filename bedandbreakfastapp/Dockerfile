FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM maven:3-eclipse-temurin-21

COPY --from=builder /src/target/assessment-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_DATA_MONGODB_URI=

EXPOSE ${SPRING_DATA_MONGODB_URI}
EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar