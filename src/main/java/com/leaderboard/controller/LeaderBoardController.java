package com.leaderboard.controller;

import com.leaderboard.model.Score;
import com.leaderboard.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/leader-boards")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @PostMapping(value = "/scores")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Score create(@Valid @RequestBody final Score score) {
        leaderBoardService.addScore(score);
        return score;
    }


    @GetMapping(value = "/v2/top-n")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Score> getTopNFromRedis(@Valid @RequestParam final int limit) {
        return leaderBoardService.getTopNFromRedis(limit);
    }

    @GetMapping(value = "/v1/top-n")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Score> getTopNFromMongo(@Valid @RequestParam final int limit) {
        return leaderBoardService.getTopNFromMongo(limit);
    }
}
