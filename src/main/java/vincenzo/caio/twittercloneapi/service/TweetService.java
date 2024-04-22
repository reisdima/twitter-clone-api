package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import com.surrealdb.driver.model.QueryResult;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.dto.TweetDto;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.model.User;
import vincenzo.caio.twittercloneapi.utils.DBConnection;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {
    private final SyncSurrealDriver driver;
    private final UserService userService;

    public TweetService(UserService userService) {
        this.driver = DBConnection.getDriver();
        this.userService = userService;
    }

    public Tweet createTweetFromDto(TweetDto tweetDto) {
        return Tweet.builder()
                .payload(tweetDto.getPayload())
                .timestamp(tweetDto.getTimestamp()).build();
    }

    public Tweet createTweet(TweetDto newTweet) {
        System.out.println(newTweet);
        newTweet.setTimestamp(LocalDateTime.now().toString());
        User user = userService.getUserByEmail(newTweet.getUserEmail());
        if(user == null) {
            throw new RuntimeException("User with e-mail " + newTweet.getUser() + " not found");
        }
        Tweet tweet = this.createTweetFromDto(newTweet);
        tweet.setUser(user.getId());
        return driver.create(Tweet.DB_NAME, tweet);
    }

}
