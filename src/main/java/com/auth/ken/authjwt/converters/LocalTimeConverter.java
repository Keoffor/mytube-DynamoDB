package com.auth.ken.authjwt.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;

public class LocalTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    @Override
    public String convert( final LocalDateTime time ) {
        return time.toString();
    }

    @Override
    public LocalDateTime unconvert( final String stringValue ) {

        return LocalDateTime.parse(stringValue);
    }
}

