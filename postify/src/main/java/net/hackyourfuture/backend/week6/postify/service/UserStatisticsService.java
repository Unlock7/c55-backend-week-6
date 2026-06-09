package net.hackyourfuture.backend.week6.postify.service;

import net.hackyourfuture.backend.week6.postify.model.UserStatisticsResponse;
import net.hackyourfuture.backend.week6.postify.repository.UserStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatisticsService {

    @Autowired
    private  UserStatisticsRepository repo;

    public UserStatisticsResponse getStats(int userId) {
        return repo.getUserStats(userId);
    }

}