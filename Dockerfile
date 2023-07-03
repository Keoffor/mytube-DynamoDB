FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot JAR file to the working directory
COPY target/*.jar app.jar

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Set environment variables for AWS credentials and region
#ENV AWS_ACCESS_KEY_ID=AKIA43BJOXSG4F3Z2SG3
#ENV AWS_SECRET_ACCESS_KEY=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
#ENV AWS_REGION=us-east-2

# Set environment variable for DynamoDB local endpoint (optional if you're using AWS DynamoDB service)
ENV AWS_DYNAMODB_ENDPOINT=dynamodb.us-east-1.amazonaws.com

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
