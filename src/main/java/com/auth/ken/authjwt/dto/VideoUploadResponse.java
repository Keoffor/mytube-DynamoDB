package com.auth.ken.authjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoUploadResponse {
    private String videoId;
    private String videoUrl;
}
