package com.auth.ken.authjwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentInfo {

    @Id
    private String id;

    private String sub;

    private String familyName;

    private String name;

    private String picture;

    private String email;

//    public void add(UserCommentInfo userInfo) {
//    }
}
