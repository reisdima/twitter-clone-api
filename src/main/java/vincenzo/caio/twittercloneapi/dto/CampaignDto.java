package vincenzo.caio.twittercloneapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CampaignDto {

    private String id;
    private String phrase;
    private LocalDateTime startTime;
}
