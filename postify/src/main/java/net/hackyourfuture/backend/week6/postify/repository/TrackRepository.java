package net.hackyourfuture.backend.week6.postify.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class TrackRepository {

    private final JdbcTemplate jdbcTemplate;

    public TrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<TrackWithArtist> findTrackWithArtistsById(int trackId) {
        String sql = """
                SELECT 
                t.track_id,
                t.track_title,
                a.artist_name
                FROM tracks t
                JOIN albums al ON t.album_id = al.album_id
                JOIN artists a ON al.artist_id = a.artist_id
                WHERE t.track_id = ?
                """;

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(new TrackWithArtist(
                    rs.getInt("track_id"),
                    rs.getString("track_title"),
                    rs.getString("artist_name")
            ));

        }, trackId);
    }
    public record TrackWithArtist(int trackId, String trackTitle, String artistName) {}
}
