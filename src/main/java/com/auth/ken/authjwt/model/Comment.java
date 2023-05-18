package com.auth.ken.authjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;
    private String text;
    private String authorid;
    private Integer likeCount;
    private Integer dislikeCount;
    private String userName;
    private String picture;
    private LocalDateTime postedDateAndTime;
}
