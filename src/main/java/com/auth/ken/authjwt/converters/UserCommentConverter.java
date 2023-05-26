package com.auth.ken.authjwt.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.auth.ken.authjwt.model.Comment;
import com.auth.ken.authjwt.model.UserCommentInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class UserCommentConverter implements DynamoDBTypeConverter<List<String>, List<UserCommentInfo>> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> convert(List<UserCommentInfo> comment) {

        List<String> serializedList= new ArrayList<>();


        // serialized the list of Comment object to a list of json strings using ObjectMapper
        for(UserCommentInfo customType: comment  ){
            try{
                objectMapper.registerModule(new JavaTimeModule());
                String serialized = objectMapper.writeValueAsString(customType);
                serializedList.add(serialized);
                log.info("fetch json string object {}", serializedList);
            }catch (IOException e) {
                throw new UncheckedIOException("Unable to serialize object", e);
            }
        }
        return serializedList;
    }


    @Override
    public List<UserCommentInfo> unconvert(List<String> stringValue) {
        objectMapper.registerModule(new JavaTimeModule());
        List<UserCommentInfo> details = new ArrayList<>();
        for (String string : stringValue) {
            UserCommentInfo comments;
            try {
                comments = objectMapper.readValue(string, UserCommentInfo.class);
                details.add(comments);
            } catch (IOException e) {
                throw new UncheckedIOException("Unable to serialize object", e);
            }
        }
        return details;
    }
}
