package net.hackyourfuture.backend.week6.postify.service;

import net.hackyourfuture.backend.week6.postify.exception.LyricsNotFoundException;
import net.hackyourfuture.backend.week6.postify.exception.TrackNotFoundException;
import net.hackyourfuture.backend.week6.postify.dto.LyricsApiResponse;
import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class TrackLyricsService {

    private final TrackRepository trackRepository;
    private final RestClient lyricsRestClient;

    public TrackLyricsService(TrackRepository trackRepository,
                              RestClient lyricsRestClient) {
        this.trackRepository = trackRepository;
        this.lyricsRestClient = lyricsRestClient;
    }

    public TrackLyricsResponse getLyrics(Long trackId) {

        TrackRepository.TrackWithArtist track =
                trackRepository.findTrackWithArtistsById(trackId.intValue())
                        .orElseThrow(() ->
                                new TrackNotFoundException(trackId.intValue()));

        try {

            LyricsApiResponse lyricsResponse =
                    lyricsRestClient.get()
                            .uri("/{artist}/{title}",
                                    track.artistName(),
                                    track.trackTitle())
                            .retrieve()
                            .body(LyricsApiResponse.class);

            return new TrackLyricsResponse(
                    track.trackId(),
                    track.trackTitle(),
                    track.artistName(),
                    lyricsResponse.getLyrics()
            );

        } catch (HttpClientErrorException.NotFound exception) {

            throw new LyricsNotFoundException(
                    track.artistName(),
                    track.trackTitle()
            );
        }
    }
}