
package net.hackyourfuture.backend.week6.postify.controller;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.service.TrackLyricsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks")
public class TrackLyricsController {

    private final TrackLyricsService trackLyricsService;

    public TrackLyricsController(
            TrackLyricsService trackLyricsService) {

        this.trackLyricsService = trackLyricsService;
    }

    @GetMapping("/{id}/lyrics")
    public TrackLyricsResponse getLyrics(
            @PathVariable Long id) {

        return trackLyricsService.getLyrics(id);
    }
}