package vincenzo.caio.twittercloneapi.dto;

import lombok.Data;
import vincenzo.caio.twittercloneapi.model.User;

import java.time.LocalDateTime;

@Data
public class TweetDto {

    private String id;
    private String payload;
    private String userId;
    private User user;
    private String userEmail;
    private LocalDateTime timestamp;
}
