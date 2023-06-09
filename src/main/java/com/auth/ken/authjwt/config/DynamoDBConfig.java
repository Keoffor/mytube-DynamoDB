package com.auth.ken.authjwt.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@EnableDynamoDBRepositories(basePackages = "com.auth.ken.authjwt.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${AWS_ACCESS_KEY_ID}")
    private String amazonAWSAccessKey;

    @Value("${AWS_SECRET_ACCESS_KEY}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB( AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-east-1"))
                .withCredentials(awsCredentialsProvider).build();
    }

    @Bean
    @Primary
    public AWSCredentialsProvider awsCrendentialsProvider () {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }
//    @Value("${amazon.access.key}")
//    private String awsAccessKey;
//
//    @Value("${amazon.access.secret-key}")
//    private String awsSecretKey;
//
//    @Value("${amazon.end-point.url}")
//    private String awsDynamoDBEndPoint;

//    @Bean
//    public AWSCredentials amazonAWSCredentials() {
//        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
//    }
//
//    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
//        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
//    }
//
//    @Bean
//    public AmazonDynamoDB amazonDynamoDB() {
//        return AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint, ""))
//                .withCredentials(amazonAWSCredentialsProvider())
//                .build();
//    }
//
//    @Bean
//    public DynamoDBMapper mapper() {
//        return new DynamoDBMapper(amazonDynamoDB());
//    }
}
