package com.auth.ken.authjwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Document(value = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullname;
    private String sub;
    private String emailAddress;
    private String name;
    private String picture;

    private Set<String> subcribedToUsers = ConcurrentHashMap.newKeySet();;
    private Set<String> subscribers = ConcurrentHashMap.newKeySet();;
    private Set<String> videoHistory = ConcurrentHashMap.newKeySet();
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> dislikedVideos = ConcurrentHashMap.newKeySet();

    public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void addToDisLikedVideos(String videoId) {
        dislikedVideos.add(videoId);
    }

    public void removeFromLikedVideo(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDislikedVideo(String videoId) {
        dislikedVideos.remove(videoId);
    }

    public void addVideoToHistory(String videoId) {
       videoHistory.add(videoId);
    }

    public void addSubcribedToUsers(String userId) {
        subcribedToUsers.add(userId);
    }
    public void removeFromSubcribedToUsers(String userId) {
        subcribedToUsers.remove(userId);
    }
    public void addToSubscribers(String userId) {
      subscribers.add(userId);
    }


    public void removeFromSubscribers(String userId) {
        subscribers.remove(userId);
    }
}
