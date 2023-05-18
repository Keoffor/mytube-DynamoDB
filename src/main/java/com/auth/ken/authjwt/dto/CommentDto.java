package com.auth.ken.authjwt.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentText;
    private String authorId;
    private String userName;
    private String picture;
    private LocalDateTime postedDateAndTime;
}
