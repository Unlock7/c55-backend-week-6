package net.hackyourfuture.backend.week6.postify.repository;

import net.hackyourfuture.backend.week6.postify.model.UserStatisticsResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Repository
public class UserStatisticsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public UserStatisticsResponse getUserStats(int userId) {
        String sql = """
                SELECT
             u.user_id AS user_id,
             u.user_name AS user_name,
             u.user_country AS user_country,
             COUNT(s.stream_id) AS total_streams,
             COUNT(DISTINCT t.track_id) AS unique_tracks,
             COUNT(DISTINCT ar.artist_id) AS unique_artists,
        (
             SELECT t2.genre
             FROM streams s2
             JOIN tracks t2 ON s2.track_id = t2.track_id
             JOIN albums al2 ON t2.album_id = al2.album_id
             JOIN artists ar2 ON al2.artist_id = ar2.artist_id
             WHERE s2.user_id = u.user_id
             GROUP BY t2.genre
             ORDER BY COUNT(*) DESC
             LIMIT 1
        )    AS favorite_genre,
             SUM(t.track_duration_s) AS total_listening_seconds
             FROM users u
             LEFT JOIN streams s ON u.user_id = s.user_id
             LEFT JOIN tracks t ON s.track_id = t.track_id
             LEFT JOIN albums al ON t.album_id = al.album_id
             LEFT JOIN artists ar ON al.artist_id = ar.artist_id
             WHERE u.user_id = ?
             GROUP BY u.user_id, u.user_name, u.user_country
        """;

        List<UserStatisticsResponse> result = jdbc.query(sql, (rs, rowNum) -> {
            UserStatisticsResponse r = new UserStatisticsResponse();
            r.userId = rs.getInt("user_id");
            r.userName = rs.getString("user_name");
            r.userCountry = rs.getString("user_country");
            r.totalStreams = rs.getInt("total_streams");
            r.uniqueTracksStreamed = rs.getInt("unique_tracks");
            r.uniqueArtistsStreamed = rs.getInt("unique_artists");
            r.favoriteGenre = rs.getString("favorite_genre");
            r.totalListeningTimeSeconds = rs.getInt("total_listening_seconds");
            return r;
        }, userId);

        return result.isEmpty() ? null : result.get(0);
    }
}
