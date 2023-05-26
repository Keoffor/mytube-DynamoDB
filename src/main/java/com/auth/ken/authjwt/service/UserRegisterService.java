package com.auth.ken.authjwt.service;

import com.auth.ken.authjwt.dto.UserInfoDTO;
import com.auth.ken.authjwt.dto.UserResponse;
import com.auth.ken.authjwt.model.User;
import com.auth.ken.authjwt.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;

    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndpoint;

    public UserResponse registerUser(String tokenValue) {
        //make a call to userinfo endpoint
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        try {
            HttpResponse<String>response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            //convert jsonString to jsonObject and store user data to database.
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDTO userInfoDTO = objectMapper.readValue(body, UserInfoDTO.class);
            Optional<User> userBySubject = userRepository.findBySub(userInfoDTO.getSub());
            if(userBySubject.isPresent()){
                return UserResponse.builder().id(userBySubject.get().getId())
                                .name(userInfoDTO.getName())
                        .picture(userInfoDTO.getPicture()).build();
            }else {
            User user = new User();
            Set<String> subscribedToUsers = new HashSet<>();
            subscribedToUsers.add("subscribersToUsers");
            Set<String> subscribers = new HashSet<>();
            subscribers.add("subscribers");
            Set<String> historyVideo = new HashSet<>();
            historyVideo.add("historyVideo");
            Set<String> likedVideos = new HashSet<>();
            likedVideos.add("likedVideos");
            Set<String> dislikedVideo = new HashSet<>();
            dislikedVideo.add("dislikedVideos");

            user.setFirstName(userInfoDTO.getGivenName());
            user.setLastName(userInfoDTO.getFamilyName());
            user.setFullname(userInfoDTO.getName());
            user.setEmailAddress(userInfoDTO.getEmail());
            user.setSub(userInfoDTO.getSub());
            user.setName(userInfoDTO.getName());
            user.setPicture(userInfoDTO.getPicture());
            user.setSubcribedToUsers(subscribedToUsers);
            user.setSubscribers(subscribers);
            user.setVideoHistory(historyVideo);
            user.setDislikedVideos(dislikedVideo);
            user.setLikedVideos(likedVideos);
            userRepository.save(user);
               return UserResponse.builder().id(user.getId())
                        .name(user.getName())
                        .picture(user.getPicture()).build();
            }
        }catch (Exception ex){
            throw  new RuntimeException("Exception occur while registering user "+ ex);
        }

        //fetch the details and save user to the database
    }
}
