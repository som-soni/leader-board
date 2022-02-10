package com.leaderboard.service;

import com.leaderboard.model.Score;
import com.leaderboard.repository.ScoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {

    private static final String LEADERBOARD = "LEADERBOARD";

    private final RedisTemplate<String, String> redisTemplate;

    private final ScoreRepository scoreRepository;

    public LeaderBoardService(final RedisTemplate<String, String> redisTemplate,
                              ScoreRepository scoreRepository) {
        this.redisTemplate = redisTemplate;
        this.scoreRepository = scoreRepository;
    }

    public void addScore(Score score) {
        scoreRepository.save(score);
        redisTemplate.opsForZSet().add(LEADERBOARD, score.getUserId(), score.getScore());

    }

    public List<Score> getTopNFromRedis(int n) {
        Set<ZSetOperations.TypedTuple<String>> leaders = redisTemplate.opsForZSet()
                .reverseRangeWithScores(LEADERBOARD, 0, n);
        return Objects.requireNonNull(leaders).stream()
                .map(tuple -> Score.builder().userId(tuple.getValue()).score(tuple.getScore()).build())
                .collect(Collectors.toList());
    }

    public List<Score> getTopNFromMongo(int n) {
        Page<Score> leaders = scoreRepository.findAll(PageRequest.of(0, n, Sort.by(Order.desc("score"))));
        return leaders.getContent();
    }
}
