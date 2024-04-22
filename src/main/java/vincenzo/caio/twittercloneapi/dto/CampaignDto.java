package vincenzo.caio.twittercloneapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CampaignDto {

    String phrase;
    LocalDateTime startTime;
}
