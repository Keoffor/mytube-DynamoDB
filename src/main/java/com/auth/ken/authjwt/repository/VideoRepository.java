package com.auth.ken.authjwt.repository;

import com.auth.ken.authjwt.model.Video;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

    List<Video> findAll();
}
