package com.auth.ken.authjwt.repository;

import com.auth.ken.authjwt.model.UserCommentInfo;
import com.auth.ken.authjwt.model.Video;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

}
