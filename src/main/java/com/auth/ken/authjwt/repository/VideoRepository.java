package com.auth.ken.authjwt.repository;

import com.auth.ken.authjwt.model.UserCommentInfo;
import com.auth.ken.authjwt.model.Video;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface VideoRepository extends CrudRepository<Video, String> {
Optional<Video>findById(String value);
}
