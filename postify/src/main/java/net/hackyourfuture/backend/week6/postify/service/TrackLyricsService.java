package net.hackyourfuture.backend.week6.postify.service;

import net.hackyourfuture.backend.week6.postify.model.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository.TrackWithArtist;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class TrackLyricsService {

    private final TrackRepository trackRepository;
    private final RestClient lyricsRestClient;

    public TrackLyricsService(TrackRepository trackRepository, RestClient lyricsRestClient) {
        this.trackRepository = trackRepository;
        this.lyricsRestClient = lyricsRestClient;
    }

    public TrackLyricsResponse getLyricsForTrack(int trackId) {
        TrackWithArtist track = trackRepository.findTrackWithArtistById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));

        String artist = track.artistName();
        String title = track.trackTitle();

        LyricsApiResponse apiResponse;
        try {
            apiResponse = lyricsRestClient.get()
                    .uri("/{artist}/{title}", encode(artist), encode(title))
                    .retrieve()
                    .body(LyricsApiResponse.class);
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new LyricsNotFoundException(trackId);
            }
            throw ex;
        }

        if (apiResponse == null || apiResponse.lyrics() == null || apiResponse.lyrics().isBlank()) {
            throw new LyricsNotFoundException(trackId);
        }

        return new TrackLyricsResponse(
                track.trackId(),
                track.trackTitle(),
                track.artistName(),
                apiResponse.lyrics()
        );
    }

    private String encode(String value) {
        return value.replace(" ", "+");
    }

    public record LyricsApiResponse(String lyrics) {}
}
