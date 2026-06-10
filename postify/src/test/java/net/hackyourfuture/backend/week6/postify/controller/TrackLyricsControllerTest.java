package net.hackyourfuture.backend.week6.postify.controller;

import net.hackyourfuture.backend.week6.postify.exception.GlobalExceptionHandler;
import net.hackyourfuture.backend.week6.postify.exception.LyricsNotFoundException;
import net.hackyourfuture.backend.week6.postify.exception.TrackNotFoundException;
import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.service.TrackLyricsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TrackLyricsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrackLyricsService trackLyricsService;

    @InjectMocks
    private TrackLyricsController trackLyricsController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(trackLyricsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnLyrics_whenTrackExistsAndLyricsAvailable() throws Exception {

        TrackLyricsResponse response = new TrackLyricsResponse(
                41,
                "LUNCH",
                "Billie Eilish",
                "I'm doing good, I'm on some new shit..."
        );

        when(trackLyricsService.getLyrics(41L))
                .thenReturn(response);

        mockMvc.perform(get("/tracks/41/lyrics"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trackId").value(41))
                .andExpect(jsonPath("$.trackTitle").value("LUNCH"))
                .andExpect(jsonPath("$.artistName").value("Billie Eilish"))
                .andExpect(jsonPath("$.lyrics")
                        .value("I'm doing good, I'm on some new shit..."));
    }

    @Test
    void shouldReturn404_whenTrackDoesNotExist() throws Exception {

        when(trackLyricsService.getLyrics(999L))
                .thenThrow(new TrackNotFoundException(999));

        mockMvc.perform(get("/tracks/999/lyrics"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Track with id 999 not found"));
    }

    @Test
    void shouldReturn404_whenLyricsNotAvailable() throws Exception {

        when(trackLyricsService.getLyrics(83L))
                .thenThrow(new LyricsNotFoundException(
                        "Tyler the Creator",
                        "WUSYANAME"
                ));

        mockMvc.perform(get("/tracks/83/lyrics"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Lyrics not found for 'WUSYANAME' by Tyler the Creator"));
    }
}