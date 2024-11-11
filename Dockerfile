# 단계 1: 빌드 단계 (Gradle 빌드)
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY gradlew ./gradlew
COPY gradle/ ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew clean build -x test --no-daemon
RUN ls -la build/libs  # 빌드된 JAR 파일이 있는지 확인

# 단계 2: 실행 단계
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app.jar"]
