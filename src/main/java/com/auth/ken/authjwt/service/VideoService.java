package com.auth.ken.authjwt.service;

import com.auth.ken.authjwt.dto.*;
import com.auth.ken.authjwt.model.Comment;
import com.auth.ken.authjwt.model.User;
import com.auth.ken.authjwt.model.UserCommentInfo;
import com.auth.ken.authjwt.model.Video;
import com.auth.ken.authjwt.repository.VideoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public VideoUploadResponse uploadVideos(MultipartFile multipartFile){
       String videourl = s3Service.uploadFile(multipartFile);
       var video = new Video();
       video.setVideourl(videourl);
       var savedVideo = videoRepository.save(video);
       return new VideoUploadResponse(savedVideo.getId(), savedVideo.getVideourl());
    }

    public VideoDTO editVideo(VideoDTO videoDTO) {
        //find the video by id
       var videoSaved = getVideoById(videoDTO.getId());

        // map the videoDTo to video
          videoSaved.setDescription(videoDTO.getDescription());
          videoSaved.setTags(videoDTO.getTags());
          videoSaved.setStatus(videoDTO.getVideoStatus());
          videoSaved.setTitle(videoDTO.getTitle());
        //save the video
        videoRepository.save(videoSaved);
        return videoDTO;
    }

    public String uploadThumbnails(MultipartFile file, String videoId) {
      var savedVideo = getVideoById(videoId);
      String thumbnailUrl = s3Service.uploadFile(file);
      savedVideo.setThumbnailurl(thumbnailUrl);
      videoRepository.save(savedVideo);
      return thumbnailUrl;
    }
    Video getVideoById(String id){
       return videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cannot find video by "+ id));
    }

    public VideoDTO videoDetails(String videoId) {


        Video saveVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find video by "+ videoId));
        increasedVideoCount(saveVideo);
        userService.addVideoToHistory(videoId);
        return mapToVideoDTO(saveVideo);
    }

    public VideoDTO likeVideo(String videoId) {
        //get video by id
        Video videoById = getVideoById(videoId);
        //increment like count
        //if the user already liked the video, then decrement liked count
        //like - 0, dislike -0
        //like - 1, dislike - 0
        //like - 0, dislike - 0

        //like - 0, dislike - 1,
        //like - 1, dislike - 0
        if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removedFromLikedVideos(videoId);

        }else if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDisLikedVideo(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideo(videoId);

        }else {
            videoById.incrementLikes();
            userService.addToLikedVideo(videoId);
        }
        videoRepository.save(videoById);
        return mapToVideoDTO(videoById);
    }

    public VideoDTO disLikeVideo(String videoId) {
        Video videoById = getVideoById(videoId);
        if(userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDislikes();
            userService.removeFromDisLikedVideo(videoId);
        }else if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removedFromLikedVideos(videoId);
            videoById.incrementDislikes();
            userService.addToDisLikedVideo(videoId);
        }else {
            videoById.incrementDislikes();
            userService.addToDisLikedVideo(videoId);
        }
        videoRepository.save(videoById);
        return mapToVideoDTO(videoById);
    }

    private VideoDTO mapToVideoDTO(Video videoById){
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle(videoById.getTitle());
        videoDTO.setId(videoById.getId());
        videoDTO.setDescription(videoById.getDescription());
        videoDTO.setVideoStatus(videoById.getStatus());
        videoDTO.setTags(videoById.getTags());
        videoDTO.setThumbnailurl(videoById.getThumbnailurl());
        videoDTO.setVideourl(videoById.getVideourl());
        videoDTO.setLikeCount(videoById.getLikes().get());
        videoDTO.setDisLikeCount(videoById.getDislikes().get());
        videoDTO.setViewCount(videoById.getViewCount().get());
        return videoDTO;
    }

    public void increasedVideoCount(Video videoId){
        videoId.incrementViewCount();
        videoRepository.save(videoId);
    }


    public UserResponse addComment(String videoId, CommentDto commentDto) {
        //get video by id
        Video video = getVideoById(videoId);
        UserResponse userResponse = new UserResponse();
        //retrieve the current user
        User user = userService.getCurrentUser();
        //map current user to video property - userCommentInfo
        UserCommentInfo userInfos = mapToUserComment(user);
        video.addUserInfo(userInfos);
        videoRepository.save(video);

        Optional<UserCommentInfo> userCommentInfo = video.getUserInfos().stream().filter(u ->
                u.getId().equals(user.getId())).findFirst();
        if(userCommentInfo.isPresent()){
            userResponse.setPicture(userCommentInfo.get().getPicture());
            userResponse.setId(userCommentInfo.get().getId());
            userResponse.setName(userCommentInfo.get().getName());
            //add user comment
            Comment comment = new Comment();
            comment.setText(commentDto.getCommentText());
            comment.setAuthorid(commentDto.getAuthorId());
            comment.setUserName(userResponse.getName());
            comment.setPicture(userResponse.getPicture());
            video.addComment(comment);
            videoRepository.save(video);

            return userResponse;

        }else {
            throw new RuntimeException("user does not exist"+userResponse.getId());
        }

    }

    public List<CommentDto> getAllComments(String videoId) {

        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getCommentList();
         return commentList.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getText());
        commentDto.setAuthorId(comment.getAuthorid());
        commentDto.setUserName(comment.getUserName());
        commentDto.setPicture(comment.getPicture());
        return commentDto;
    }

    private UserCommentInfo mapToUserComment(User user){
        UserCommentInfo userCommentInfo = new UserCommentInfo();
        userCommentInfo.setEmail(user.getEmailAddress());
        userCommentInfo.setName(user.getName());
        userCommentInfo.setId(user.getId());
        userCommentInfo.setPicture(user.getPicture());
        return userCommentInfo;
    }

    public List<VideoDTO> getAllVideos() {

         List<Video> result = videoRepository.findAll();
        return result.stream().map(this::mapToVideoDTO).collect(Collectors.toList());
    }
}
