package com.auth.ken.authjwt.controller;

import com.auth.ken.authjwt.dto.*;
import com.auth.ken.authjwt.model.Comment;
import com.auth.ken.authjwt.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VideoController {
    private final VideoService videoService;

    @PostMapping(value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoUploadResponse uploadFile(@RequestParam("file") MultipartFile file){
      return videoService.uploadVideos(file);
    }


    @PostMapping(value = "/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnial(@RequestParam("file") MultipartFile file, @RequestParam("videoId")String videoId){
        return videoService.uploadThumbnails(file, videoId);
    }

    @PutMapping(value = "saved")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO editVideoMetadata(@RequestBody VideoDTO videoDTO){

        return videoService.editVideo(videoDTO);
    }

    @GetMapping(value = "/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO getVideoDetails(@PathVariable String videoId){
     return videoService.videoDetails(videoId);
    }

    @PostMapping(value = "/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO likeVideos(@PathVariable String videoId){
        return videoService.likeVideo(videoId);
    }

    @PostMapping(value = "/{videoId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO dislikeVideos(@PathVariable String videoId){
        return videoService.disLikeVideo(videoId);
    }

    @PostMapping(value = "/{videoId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addComment(@PathVariable String videoId, @RequestBody CommentDto commentDto){
      return   videoService.addComment(videoId, commentDto);
    }
    @GetMapping(value = "/{videoId}/comments")
    public List<CommentDto> getAllComment(@PathVariable String videoId){
        return videoService.getAllComments(videoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDTO> getAllVideo(){
        return videoService.getAllVideos();
    }

}
