package vincenzo.caio.twittercloneapi.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
public class User {

    public final static String DB_NAME = "user";

    private String id;
    private String username;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private String password;

}
