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
//
//		Table table = dynamoDB.getTable("video");
//		table.delete();
//		table.waitForDelete();
//		createTable();
		try {

			Table table = dynamoDB.getTable("user");
			Item item = new Item()
					.withString("id", "01")
					.withString("firstname", "john")
					.withString("lastname", "doe")
					.withString("fullname", "john doe")
					.withString("sub", "xyz")
					.withString("emailaddress", "johndoe@yupmail.com")
					.withString("name", "john")
					.withString("picture", "https://picture.com")
					.withStringSet("subscribed_to_users", new HashSet<>(List.of("johnDoe")))
					.withStringSet("subscribers", new HashSet<>(List.of("DoeJohn")))
					.withStringSet("videohistory", new HashSet<>(List.of("6460c91e11b78d455e95bbcd")))
					.withStringSet("likedvideos", new HashSet<>(List.of("DoeJohn")))
					.withStringSet("dislikedvideos", new HashSet<>(List.of("DoeJohn")));
			PutItemOutcome outcome = table.putItem(item);
			System.out.println(outcome);
		}catch (Exception e) {
			System.err.println("Create items failed.");
			System.err.println(e.getMessage());

		}
//write to video table
//		DynamoDBMapper mapper = new DynamoDBMapper(client);
//		List<Comment> comments = new ArrayList<>();
//		comments.add(new Comment("01","great video", "john", 1,0,
//				"foo","bright", LocalDateTime.now()));
//
//		List<UserCommentInfo> userInfo = new ArrayList<>();
//		userInfo.add(new UserCommentInfo("02", "johnfoo","johndoes","does",
//				"photo","email"));
//		try{
//        Video vidoes = new Video();
//		vidoes.setId("344");
//		vidoes.setUserInfos(userInfo);
//		vidoes.setCommentList(comments);
//		vidoes.setTags(null);
//		vidoes.setStatus(null);
//		vidoes.setTitle("John");
//		vidoes.setLikes(null);
//		vidoes.setDislikes(null);
//		vidoes.setVideourl("https://videoul.com");
//		vidoes.setThumbnailurl("photo.jpg");
//		vidoes.setPostedDateAndTime(LocalDateTime.now());
//		vidoes.setDescription("best buy");
//		vidoes.setUserId("233xy");
//		mapper.save(vidoes);
//
//
//		System.out.println(vidoes);
//	}catch (Exception e) {
//		System.err.println("Create items failed.");
//		System.err.println(e.getMessage());
//
//	}
//
//


	}

	static void createTable(){
		try {
			List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("id").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("userId").withAttributeType("S"));

			List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH));
			keySchema.add(new KeySchemaElement().withAttributeName("userId").withKeyType(KeyType.RANGE));

			CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(6L));
			System.out.println("Issuing CreateTable request for " + tableName);
			Table table = dynamoDB.createTable(request);

			System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
			table.waitForActive();
			getTableInformation();

		} catch (Exception x) {
			System.err.println("CreateTable request failed for " + tableName);
			System.err.println(x.getMessage());
		}
	}

	static void getTableInformation() {
		System.out.println("Describing " + tableName);

		TableDescription tableDescription = dynamoDB.getTable(tableName).describe();
		System.out.format(
				"Name: %s:\n" + "Status: %s \n" + "Provisioned Throughput (read capacity units/sec): %d \n"
						+ "Provisioned Throughput (write capacity units/sec): %d \n",
				tableDescription.getTableName(), tableDescription.getTableStatus(),
				tableDescription.getProvisionedThroughput().getReadCapacityUnits(),
				tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
	}
}

