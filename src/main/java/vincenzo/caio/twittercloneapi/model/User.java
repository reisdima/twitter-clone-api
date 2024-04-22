package vincenzo.caio.twittercloneapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    public static String DB_NAME = "user";

    private String id;
    private String username;
    private String name;
    private String email;
    private String password;

}
