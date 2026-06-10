package net.hackyourfuture.backend.week6.postify.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


