FROM openjdk:11.0.5-jdk-slim as builder

COPY . /app
WORKDIR /app
RUN ./gradlew clean build --no-daemon

FROM openjdk:11.0.5-jre-slim
COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]