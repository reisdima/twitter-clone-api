package vincenzo.caio.twittercloneapi.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Campaign {

    public final static String DB_NAME = "campaign";

    private String id;
    private String phrase;
    private LocalDateTime startTime;

}
