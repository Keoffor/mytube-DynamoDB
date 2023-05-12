package com.auth.ken.authjwt.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentText;
    private String authorId;
    private String userName;
    private String picture;
}
