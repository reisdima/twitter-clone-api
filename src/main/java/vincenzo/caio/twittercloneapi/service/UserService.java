package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import com.surrealdb.driver.model.QueryResult;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.dto.CampaignDto;
import vincenzo.caio.twittercloneapi.dto.UserDto;
import vincenzo.caio.twittercloneapi.exception.EntityNotFoundException;
import vincenzo.caio.twittercloneapi.exception.InvalidInputFormatException;
import vincenzo.caio.twittercloneapi.model.Campaign;
import vincenzo.caio.twittercloneapi.utils.DBConnection;
import vincenzo.caio.twittercloneapi.model.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final SyncSurrealDriver driver;

    public UserService() {
        this.driver = DBConnection.getDriver();
    }

    public User createUserFromDto (UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .createdAt(userDto.getCreatedAt())
                .username(userDto.getUsername())
                .password(userDto.getPassword()).build();
    }

    public User createUser(UserDto newUser) {
        newUser.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        List<QueryResult<User>> query = driver.query("SELECT email FROM user WHERE email=$email", Map.of("email", newUser.getEmail()), User.class);
        if(query.isEmpty() || query.get(0).getResult().isEmpty()) {
            newUser.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
            return driver.create(User.DB_NAME, createUserFromDto(newUser));
        }
        throw new InvalidInputFormatException("The e-mail " + newUser.getEmail() + " is already in use.");
    }

    public User updateUser(UserDto userDto) {
        List<QueryResult<User>> query = driver.query("UPDATE $id SET name=$name",
                Map.of("id", userDto.getId(), "name", userDto.getName()), User.class);
        if(query.isEmpty() || query.get(0).getResult().isEmpty()) {
            throw new RuntimeException("An error occurred trying to update user " + userDto.getId());
        }
        return query.get(0).getResult().get(0);

    }

    public User getUserByEmail(String email) {
        List<QueryResult<User>> query = driver.query("SELECT * FROM user WHERE email=$email", Map.of("email", email), User.class);

        if(query.isEmpty()) {
            return null;
        }
        if(query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult().get(0);
    }


}
