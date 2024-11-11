# Backend Dockerfile (Spring Boot)
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# 필요한 파일 복사
COPY gradlew ./
COPY gradle/ ./gradle/
COPY build.gradle settings.gradle ./


# gradlew 실행 권한 추가
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY . .
# Gradle 빌드 실행
RUN ./gradlew clean build -x test --no-daemon

# 실행 단계
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 5000

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
