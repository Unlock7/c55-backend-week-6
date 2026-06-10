package net.hackyourfuture.backend.week6.postify.controller;

import net.hackyourfuture.backend.week6.postify.model.UserStatisticsResponse;
import net.hackyourfuture.backend.week6.postify.service.UserStatisticsService;
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

class UserStatisticsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserStatisticsService userStatisticsService;

    @InjectMocks
    private UserStatisticsController userStatisticsController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userStatisticsController).build();
    }

    @Test
    void shouldReturnUserStats_whenUserExists() throws Exception {
        UserStatisticsResponse mockResponse = new UserStatisticsResponse(
                1, "lena_v", "NL", 65, 34, 8, "Nederpop", 14021
        );

        when(userStatisticsService.getStats(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/users/1/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.userName").value("lena_v"))
                .andExpect(jsonPath("$.totalStreams").value(65));
    }

    @Test
    void shouldReturn404_whenUserDoesNotExist() throws Exception {
        when(userStatisticsService.getStats(999)).thenReturn(null);

        mockMvc.perform(get("/users/999/statistics"))
                .andExpect(status().isNotFound());
    }
}

