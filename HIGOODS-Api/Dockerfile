FROM openjdk:17-alpine

EXPOSE 8080

COPY ./build/libs/*.jar app.jar
COPY ./src/main/resources/static /app/src/main/resources/static
ARG PROFILE=dev
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]