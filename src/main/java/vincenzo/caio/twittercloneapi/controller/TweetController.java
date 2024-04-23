package vincenzo.caio.twittercloneapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vincenzo.caio.twittercloneapi.dto.TweetDto;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.service.TweetService;


import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService service;

    public TweetController(TweetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> tweet(@RequestBody TweetDto tweet) {
        Tweet newTweet = service.createTweet(tweet);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTweet);
    }

    @GetMapping("/betweenTime")
    public ResponseEntity<?> getTweetsBetweenTime(@RequestParam(required = false) String startTime, @RequestParam String endTime) {
        List<Tweet> tweets = service.getTweetsBetweenTime(startTime, endTime);
        return ResponseEntity.status(HttpStatus.OK).body(tweets);
    }


}
