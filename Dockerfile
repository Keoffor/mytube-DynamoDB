FROM openjdk:17-alpine
ENV AWS_ACCESS_KEY_ID=value1
ENV AWS_SECRET_ACCESS_KEY=value2
ENV AWS_REGION=us-east-1
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]