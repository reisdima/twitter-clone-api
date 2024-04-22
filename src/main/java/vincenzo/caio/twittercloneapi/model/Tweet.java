package vincenzo.caio.twittercloneapi.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class Tweet {

    public static String DB_NAME = "tweet";

    private String id;
    private String payload;
    private String timestamp;
//    @NonNull
    private String user;
}
