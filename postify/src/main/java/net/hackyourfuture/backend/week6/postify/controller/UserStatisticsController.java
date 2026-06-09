package net.hackyourfuture.backend.week6.postify.controller;


import net.hackyourfuture.backend.week6.postify.model.UserStatisticsResponse;
import net.hackyourfuture.backend.week6.postify.service.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserStatisticsController {

    @Autowired
        private UserStatisticsService service;

        @GetMapping("/{id}/statistics")
        public ResponseEntity<?> getStats(@PathVariable int id) {
            UserStatisticsResponse stats = service.getStats(id);

            if (stats == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            return ResponseEntity.ok(stats);
        }
    }

