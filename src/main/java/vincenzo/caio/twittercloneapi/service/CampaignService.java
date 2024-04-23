package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import com.surrealdb.driver.model.QueryResult;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.dto.CampaignDto;
import vincenzo.caio.twittercloneapi.model.Campaign;
import vincenzo.caio.twittercloneapi.model.Tweet;
import vincenzo.caio.twittercloneapi.model.User;
import vincenzo.caio.twittercloneapi.utils.DBConnection;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class CampaignService {

    private final SyncSurrealDriver driver;
    private final TweetService tweetService;
    private final UserService userService;

    public CampaignService(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
        driver = DBConnection.getDriver();
    }

    public Campaign createCampaignFromDto(CampaignDto campaignDto) {
        return Campaign.builder().startTime(campaignDto.getStartTime()).phrase(campaignDto.getPhrase()).build();
    }

    public Campaign createCampaign(CampaignDto campaignDto) {
        Campaign campaign = createCampaignFromDto(campaignDto);
        campaign.setStartTime(LocalDateTime.now(ZoneOffset.UTC));
        return driver.create(Campaign.DB_NAME, campaign);
    }

    public List<Campaign> getAllCampaigns() {
        List<QueryResult<Campaign>> query = driver.query("SELECT * FROM campaign ORDER BY startTime ASC",
                null, Campaign.class);
        if(query.isEmpty() || query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult();
    }

    public int calculatePointsForUser(String email) {
        List<Campaign> campaigns = this.getAllCampaigns();
        if(campaigns.isEmpty()) {
            return 0;
        }
        User user = userService.getUserByEmail(email);
        int points = 0;
        Campaign currentCampaign;
        for (int i = 0; i < campaigns.size() - 1; i++) {
            currentCampaign = campaigns.get(i);
            LocalDateTime start = currentCampaign.getStartTime();
            LocalDateTime end = campaigns.get(i + 1).getStartTime();
            List<Tweet> tweets = tweetService.getTweetsBetweenTimeByUser(user, start, end);
            if(tweets != null) {
                for (Tweet tweet : tweets) {
                    if(tweet.getPayload().contains(currentCampaign.getPhrase())) {
                        points += 10;
                    }
                }
            }
        }
        currentCampaign = campaigns.get(campaigns.size() - 1);
        LocalDateTime start = currentCampaign.getStartTime();
        List<Tweet> tweets = tweetService.getTweetsAfterTimeByUser(user, start);
        if(tweets != null) {
            for (Tweet tweet : tweets) {
                if(tweet.getPayload().contains(currentCampaign.getPhrase())) {
                    points += 10;
                }
            }
        }
        return points;
    }

}
