package com.leaderboard.repository;

import com.leaderboard.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<Score, String> {

    @Override
    Page<Score> findAll(Pageable pageable);
}
