package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import com.surrealdb.driver.model.QueryResult;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.dto.TweetDto;
import vincenzo.caio.twittercloneapi.exception.EntityNotFoundException;
import vincenzo.caio.twittercloneapi.exception.InvalidInputFormatException;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.model.User;
import vincenzo.caio.twittercloneapi.utils.DBConnection;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
public class TweetService {
    private final SyncSurrealDriver driver;
    private final UserService userService;

    private final Integer PAGE_SIZE = 1;

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
        newTweet.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        User user = userService.getUserByEmail(newTweet.getUserEmail());
        if (user == null) {
            throw new EntityNotFoundException("User", "User with e-mail " + newTweet.getUser() + " not found");
        }
        Tweet tweet = this.createTweetFromDto(newTweet);
        tweet.setUser(user.getId());
        return driver.create(Tweet.DB_NAME, tweet);
    }

    public List<Tweet> getTweetsBeforeTime(String time) {
        System.out.println("Looking for tweets before " + time);
        List<QueryResult<Tweet>> query = driver.query("SELECT * FROM tweet WHERE timestamp < $time ", Map.of("time", time), Tweet.class);
        if (query.isEmpty()) {
            return null;
        }
        if (query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult();
    }

    public List<Tweet> getTweetsBetweenTime(Instant startTime, Instant endTime) {
        System.out.println("Procurando tweets entre " + startTime + " e " + endTime);
        List<QueryResult<Tweet>> query = driver.query("SELECT * FROM tweet WHERE timestamp > $startTime AND timestamp < $endTime "
                , Map.of("startTime", startTime.toString(), "endTime", endTime.toString()
                ), Tweet.class);
        if (query.isEmpty()) {
            return null;
        }
        if (query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult();
    }


    public List<Tweet> getTweetsAfterTimeByUser(User user, LocalDateTime startTime) {
        System.out.println("Procurando tweets do usuario " + user.getEmail() + " depois de " + startTime);
        List<QueryResult<Tweet>> query = driver.query("SELECT * FROM tweet WHERE user = $user AND timestamp > $startTime",
                Map.of("user", user.getId(), "startTime", startTime.toString()), Tweet.class);
        if (query.isEmpty()) {
            return null;
        }
        if (query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult();
    }


    public List<Tweet> getAllTweetsByPage(Integer page) {
        if (page <= 0) {
            throw new InvalidInputFormatException("Page must be a positve number");
        }
        String queryStr = "SELECT * FROM tweet LIMIT " + PAGE_SIZE + " START " + ((page - 1) * PAGE_SIZE);
        List<QueryResult<Tweet>> query = driver.query(queryStr, null, Tweet.class);
        if (query.isEmpty() || query.get(0).getResult().isEmpty()) {
            throw new EntityNotFoundException("Tweet", "Could not find any tweet from page " + page);
        }
        return query.get(0).getResult();
    }

    public List<Tweet> getTweetsByUserId(String userId) {
        List<QueryResult<Tweet>> query = driver.query("SELECT * FROM tweet WHERE user=$userId",
                Map.of("userId", userId), Tweet.class);
        if (query.isEmpty() || query.get(0).getResult().isEmpty()) {
            throw new EntityNotFoundException("Tweet", "Could not find any tweet from user " + userId);
        }
        return query.get(0).getResult();
    }

    public Tweet getTweetById(String id) {
        List<QueryResult<Tweet>> query = driver.query("SELECT * FROM tweet WHERE id=$id", Map.of("id", id), Tweet.class);
        if (query.isEmpty() || query.get(0).getResult().isEmpty()) {
            throw new EntityNotFoundException("Tweet", "Could not find tweet with id " + id);
        }
        return query.get(0).getResult().get(0);
    }

    public List<Tweet> getTweetsBetweenTimeByUser(User user, LocalDateTime startTime, LocalDateTime endTime) {
        System.out.println("Procurando tweets do usuario " + user.getEmail() + " entre " + startTime + " e " + endTime);
        List<QueryResult<Tweet>> query = driver.query("SELECT * FROM tweet WHERE user = $user AND timestamp > $startTime AND timestamp < $endTime ",
                Map.of("user", user.getId(), "startTime", startTime.toString(), "endTime", endTime.toString()
                ), Tweet.class);
        if (query.isEmpty()) {
            return null;
        }
        if (query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult();
    }


}
