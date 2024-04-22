package vincenzo.caio.twittercloneapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vincenzo.caio.twittercloneapi.dto.TweetDto;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.service.TweetService;

import java.time.LocalDateTime;

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


}
