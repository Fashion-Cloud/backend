FROM openjdk:17-jdk AS builder

VOLUME /tmp

COPY ./gradlew .
# gradlew 복사
COPY ./gradle gradlew
# gradlew 복사
COPY ./build.gradle .
# build.gradlew 복사
COPY ./settings.gradle .
# settings.gradlew 복사
COPY ./src src
# 웹 어플리케이션 소스 복사

RUN chmod +x ./gradlew
# gradlew 실행권한 부여
RUN ./gradlew dependencies --no-daemon
RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/*.jar
VOLUME /tmp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]