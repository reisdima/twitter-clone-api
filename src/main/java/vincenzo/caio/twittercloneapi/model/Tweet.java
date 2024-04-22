package vincenzo.caio.twittercloneapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@Builder
public class Tweet {

    public static String DB_NAME = "user";

    private String payload;
    private ZonedDateTime timestamp;
    @NonNull
    private User user;
}
