package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.model.User;
import vincenzo.caio.twittercloneapi.utils.DBConnection;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TweetService {
    private final SyncSurrealDriver driver;

    public TweetService() {
        this.driver = DBConnection.getDriver();
    }

    public Tweet createTweet(Tweet tweet) {
        tweet.setTimestamp(ZonedDateTime.now());
        return driver.create(Tweet.DB_NAME, tweet);
    }

}
