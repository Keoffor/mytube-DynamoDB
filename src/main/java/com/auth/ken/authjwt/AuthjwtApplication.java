package com.auth.ken.authjwt;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.auth.ken.authjwt.model.Comment;
import com.auth.ken.authjwt.model.User;
import com.auth.ken.authjwt.model.UserCommentInfo;
import com.auth.ken.authjwt.model.Video;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


@SpringBootApplication
public class AuthjwtApplication {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

	static DynamoDB dynamoDB = new DynamoDB(client);

	static String tableName = "user";

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(AuthjwtApplication.class, args);
	}
}

