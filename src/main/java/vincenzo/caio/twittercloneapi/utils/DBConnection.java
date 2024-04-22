package vincenzo.caio.twittercloneapi.utils;


import com.surrealdb.connection.SurrealConnection;
import com.surrealdb.connection.SurrealWebSocketConnection;
import com.surrealdb.driver.SyncSurrealDriver;
import org.springframework.stereotype.Component;

@Component
public class DBConnection {

    private static SyncSurrealDriver driver;
    private static final String namespace = "liveSponsors";
    private static final String database = "twitter";

    public DBConnection() {
    }

    public static synchronized SyncSurrealDriver getDriver() {
        if(driver == null){
            SurrealConnection conn = new SurrealWebSocketConnection("localhost", 8000, false);
            conn.connect(5);
            driver = new SyncSurrealDriver(conn);
            driver.use(namespace, database);
        }

        return driver;
    }
}
