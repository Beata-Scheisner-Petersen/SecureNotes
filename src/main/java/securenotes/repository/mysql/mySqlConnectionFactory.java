package securenotes.repository.mysql;

import securenotes.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class mySqlConnectionFactory {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getSqlConnection () {
        RuntimeException runtimeException = new RuntimeException();
        if (URL.isBlank() || USER.isBlank() || PASSWORD.isBlank()) {
            Logger.log("Missing required environment variables", runtimeException);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            Logger.log("failed to connect with the database", sqlException);
        }

        return connection;
    }
}
