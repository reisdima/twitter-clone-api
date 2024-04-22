package vincenzo.caio.twittercloneapi.dto;

import lombok.Data;
import vincenzo.caio.twittercloneapi.model.User;

@Data
public class TweetDto {

    private String id;
    private String payload;
    private String userId;
    private User user;
    private String userEmail;
    private String timestamp;
}
