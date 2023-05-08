package com.auth.ken.authjwt.service;

import com.auth.ken.authjwt.model.User;
import com.auth.ken.authjwt.model.Video;
import com.auth.ken.authjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        //get current user identity
      String sub =  ((Jwt)(SecurityContextHolder.getContext()
              .getAuthentication().getPrincipal()))
              .getClaim("sub");
      return userRepository.findBySub(sub)
              .orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - " + sub));
    }

    public void addToLikedVideo(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToLikedVideos(videoId);
        userRepository.save(currentUser);
    }
    public void addToDisLikedVideo(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }
    public boolean ifLikedVideo(String videoId){
       return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo ->
                likedVideo.equals(videoId));
    }

    public boolean ifDisLikedVideo(String videoId){
        return getCurrentUser().getDislikedVideos().stream().anyMatch(likedVideo ->
                likedVideo.equals(videoId));
    }

    public void removedFromLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromLikedVideo(videoId);
        userRepository.save(currentUser);
    }

    public void removeFromDisLikedVideo(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromDislikedVideo(videoId);
        userRepository.save(currentUser);
    }

    public void addVideoToHistory(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addVideoToHistory(videoId);
        userRepository.save(currentUser);
    }

    public void subscribeUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.addSubcribedToUsers(userId);

        User user = getUserbyId(userId);
        user.addToSubscribers(userId);

      userRepository.save(currentUser);
      userRepository.save(user);

    }
    public void unsubscribeUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromSubcribedToUsers(userId);

        User user = getUserbyId(userId);
        user.removeFromSubscribers(userId);

        userRepository.save(currentUser);
        userRepository.save(user);

    }

    public Set<String> userHistory(String userId) {
     User user = getUserbyId(userId);
     return user.getVideoHistory();
    }

    private User getUserbyId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("cannot not find user with id "+ userId));
    }
}
