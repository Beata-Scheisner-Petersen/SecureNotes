package securenotes.repository.mysql;

import securenotes.logging.Logger;
import securenotes.repository.interfaces.IBannedWordRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class mySqlBannedWordRepository implements IBannedWordRepository {
    private final List<String> bannedWords = new ArrayList<>();
    @Override
    public List<String> getBannedWords() {
        //noinspection SqlResolve
        String sql = "SELECT word FROM banned_words";

        try (Connection connection = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                bannedWords.add(result.getString("word"));
            }
            return bannedWords;
        } catch (SQLException e) {
            System.out.println("Error: failed to fetch banned words! \n");
            Logger.log("Failed to fetch banned words", e);
        }
        return null;
    }
}
