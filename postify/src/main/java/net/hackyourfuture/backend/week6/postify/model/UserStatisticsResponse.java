package net.hackyourfuture.backend.week6.postify.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "userId",
        "userName",
        "userCountry",
        "totalStreams",
        "uniqueTracksStreamed",
        "uniqueArtistsStreamed",
        "favoriteGenre",
        "totalListeningTimeSeconds"
})

public class UserStatisticsResponse {

        public int userId;
        public  String userName;
        public String userCountry;
        public int totalStreams;
        public int uniqueTracksStreamed;
        public int uniqueArtistsStreamed;
        public String favoriteGenre;
        public int totalListeningTimeSeconds;
    }


