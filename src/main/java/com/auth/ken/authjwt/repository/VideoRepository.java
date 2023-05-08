package com.auth.ken.authjwt.repository;

import com.auth.ken.authjwt.model.Video;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
}
