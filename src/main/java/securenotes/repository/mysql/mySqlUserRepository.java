package securenotes.repository.mysql;

import securenotes.logging.Logger;
import securenotes.model.User;
import securenotes.repository.interfaces.IUserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mySqlUserRepository implements IUserRepository {

    @Override
    public User findById(int id) {
        //noinspection SqlResolve
        String sql = "SELECT id, username, password FROM users WHERE id = ?";

        try(Connection connection = mySqlConnectionFactory.getSqlConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return mapUser(result);
            }
        } catch (SQLException e) {
            Logger.log("failed to find user by id", e);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        //noinspection SqlResolve
        String sql = "SELECT id, username, password FROM users WHERE username = ?";

        try(Connection connection = mySqlConnectionFactory.getSqlConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return mapUser(result);
            }
        } catch (SQLException e) {
            Logger.log("failed to find user by id", e);
        }
        return null;
    }

    @Override
    public void save(User user) {
        //noinspection SqlResolve
        String insert = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: sql injection failed");
            Logger.log("failed to save user " + user.getUsername(), e);
        }
    }
}
