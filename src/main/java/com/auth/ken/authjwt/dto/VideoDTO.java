package com.auth.ken.authjwt.dto;

import com.auth.ken.authjwt.model.VideoStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private String id;
    private String title;
    private String description;
    private Set<String> tags;
    private String videourl;
    private VideoStatus videoStatus;
    private String thumbnailurl;
    private Integer viewCount;
    private Integer likeCount;
    private Integer disLikeCount;


}
