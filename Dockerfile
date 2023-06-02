#FROM openjdk:17-alpine
#ENV AWS_ACCESS_KEY_ID=value1
#ENV AWS_SECRET_ACCESS_KEY=value2
#ENV AWS_REGION=us-east-1
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#EXPOSE 8181
# Use an official OpenJDK as the base image
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot JAR file to the working directory
COPY target/*.jar app.jar

## Install AWS CLI for DynamoDB local (optional if you're using AWS DynamoDB service)
#RUN apt-get update && apt-get install -y \
#    unzip \
#    && rm -rf /var/lib/apt/lists/*
#RUN curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip"
#RUN unzip awscli-bundle.zip
#RUN ./awscli-bundle/install -i /usr/local/aws -b /usr/local/bin/aws

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Set environment variables for AWS credentials and region
#ENV AWS_ACCESS_KEY_ID=AKIA43BJOXSG4F3Z2SG3
#ENV AWS_SECRET_ACCESS_KEY=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
#ENV AWS_REGION=us-east-1

# Set environment variable for DynamoDB local endpoint (optional if you're using AWS DynamoDB service)
ENV AWS_DYNAMODB_ENDPOINT=dynamodb.us-east-1.amazonaws.com

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
