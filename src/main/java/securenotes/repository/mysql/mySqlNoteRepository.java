package securenotes.repository.mysql;

import securenotes.logging.Logger;
import securenotes.model.Note;
import securenotes.repository.interfaces.INoteRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class mySqlNoteRepository implements INoteRepository {
    private final List<Note> notes = new ArrayList<>();

    @Override
    public List<Note> notesListByUserId(int userId) {
        //noinspection SqlResolve
        String sql = "SELECT * FROM notes WHERE users_id = ?";

        try (Connection connection = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            notes.clear();

            while (result.next()) {
                notes.add(map(result));
            }
            return notes;
        } catch (SQLException e) {
            Logger.log("Failed to fetch notes for user by users_id", e);
        }
        return null;
    }

    @Override
    public List<Note> notesForAdmin() {
        //noinspection SqlResolve
        String sql = "SELECT * FROM notes WHERE id > ?";

        try (Connection connection = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            ResultSet result = statement.executeQuery();

            notes.clear();

            while (result.next()) {
                notes.add(map(result));
            }
            return notes;
        } catch (SQLException e) {
            Logger.log("Failed to fetch notes for user by users_id", e);
        }
        return null;
    }

    @Override
    public Note findById(int id) {
        //noinspection SqlResolve
        String sql = "SELECT * FROM notes WHERE id = ?";

        try (Connection connection = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return map(result);
            }
        } catch (SQLException e) {
            Logger.log("Failed to fetch notes for users by id", e);
        }
        return null;
    }

    @Override
    public void save(Note note) {
        //noinspection SqlResolve
        String sql = "INSERT INTO notes (users_id, title, content, createAt) VALUES (?, ?, ?, ?)";

        try (Connection conn = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, note.getUserId());
            statement.setString(2, note.getTitle());
            statement.setString(3, note.getContent());
            statement.setString(4, note.getCreated());

            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.log("Failed to save note", e);
        }
    }

    @Override
    public void update(Note note) {
        //noinspection SqlResolve
        String sql = "UPDATE notes SET title = ?, content = ?, updatedAt = ? WHERE id = ?";

        try (Connection conn = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());
            statement.setString(3, note.getUpdated());
            statement.setInt(4, note.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.log("failed to update note", e);
        }
    }

    @Override
    public void delete(int id) {
        //noinspection SqlResolve
        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.log("Failed to delete note", e);
        }
    }

    private Note map (ResultSet result) throws SQLException {
        return new Note(
                result.getInt("id"),
                result.getInt("users_id"),
                result.getString("title"),
                result.getString("content"),
                result.getString("createAt"),
                result.getString("updatedAt")
        );
    }
}
