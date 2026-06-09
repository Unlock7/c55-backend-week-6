package net.hackyourfuture.backend.week6.postify.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStatisticsResponseTest {

    @Test
    void shouldContainCorrectUserStatistics() {
        UserStatisticsResponse response = new UserStatisticsResponse();

        response.userId = 1;
        response.userName = "Abraham";
        response.userCountry = "CA";

        assertEquals(1, response.userId);
        assertEquals("Abraham", response.userName);
        assertEquals("CA", response.userCountry);
    }
}
