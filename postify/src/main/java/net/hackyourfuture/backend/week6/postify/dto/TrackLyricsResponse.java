package net.hackyourfuture.backend.week6.postify.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackLyricsResponse {
    private int trackId;
    private String trackTitle;
    private String artistName;
    private String lyrics;

}
