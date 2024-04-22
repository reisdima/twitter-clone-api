package vincenzo.caio.twittercloneapi.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Tweet {

    String payload;
    ZonedDateTime timestamp;
}
