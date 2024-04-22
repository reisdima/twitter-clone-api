package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.dto.CampaignDto;
import vincenzo.caio.twittercloneapi.model.Campaign;
import vincenzo.caio.twittercloneapi.utils.DBConnection;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class CampaignService {

    private final SyncSurrealDriver driver;

    public CampaignService() {
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
        return driver.select(Campaign.DB_NAME, Campaign.class);
    }

}
