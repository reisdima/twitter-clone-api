package vincenzo.caio.twittercloneapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.service.TweetService;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService service;

    public TweetController(TweetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> tweet(@RequestBody Tweet tweet) {
        Tweet newTweet = service.createTweet(tweet);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTweet(newTweet));
    }


}
