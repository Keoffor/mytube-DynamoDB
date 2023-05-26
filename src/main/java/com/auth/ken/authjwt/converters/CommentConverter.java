package com.auth.ken.authjwt.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.auth.ken.authjwt.model.Comment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommentConverter implements  DynamoDBTypeConverter<List<String>, List<Comment>> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public List<String> convert(List<Comment> comment) {

        List<String> serializedList= new ArrayList<>();


            // serialized the list of Comment object to a list of json strings using ObjectMapper
            for(Comment customType: comment  ){
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
    public List<Comment> unconvert(List<String> stringValue) {
        objectMapper.registerModule(new JavaTimeModule());
        List<Comment> details = new ArrayList<>();
        for (String string : stringValue) {
            Comment comments;
            try {
                comments = objectMapper.readValue(string, Comment.class);
                details.add(comments);
            } catch (IOException e) {
                throw new UncheckedIOException("Unable to serialize object", e);
            }
        }
        return details;
    }

}
