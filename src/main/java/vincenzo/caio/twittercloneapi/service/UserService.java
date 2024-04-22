package vincenzo.caio.twittercloneapi.service;

import com.surrealdb.driver.SyncSurrealDriver;
import com.surrealdb.driver.model.QueryResult;
import org.springframework.stereotype.Service;
import vincenzo.caio.twittercloneapi.utils.DBConnection;
import vincenzo.caio.twittercloneapi.model.User;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final SyncSurrealDriver driver;

    public UserService() {
        this.driver = DBConnection.getDriver();
    }
    public User createUser(User newUser) {
        List<QueryResult<User>> query = driver.query("SELECT * FROM user WHERE email=$email", Map.of("email", newUser.getEmail()), User.class);
        if(query.isEmpty() || query.get(0).getResult().isEmpty()) {
            return driver.create(User.DB_NAME, newUser);
        }
        throw new RuntimeException("The e-mail " + newUser.getEmail() + " is already in use.");
    }

    public User getUserByEmail(String email) {
        List<QueryResult<User>> query = driver.query("SELECT name,email FROM user WHERE email=$email", Map.of("email", email), User.class);

        if(query.isEmpty()) {
            return null;
        }
        if(query.get(0).getResult().isEmpty()) {
            return null;
        }
        return query.get(0).getResult().get(0);
    }
}
