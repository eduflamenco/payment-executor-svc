FROM eclipse-temurin:21.0.2_13-jdk-jammy AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
COPY src src
RUN ./gradlew build -x test


FROM eclipse-temurin:21.0.2_13-jre-jammy AS final
WORKDIR /opt/app
COPY --from=builder /app/build/libs/*.jar /opt/app/app.jar
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
