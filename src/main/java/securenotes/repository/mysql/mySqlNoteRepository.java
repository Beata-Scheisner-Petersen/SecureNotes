package securenotes.repository.mysql;

import securenotes.logging.ErrorLogger;
import securenotes.model.Note;
import securenotes.repository.interfaces.INoteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class mySqlNoteRepository implements INoteRepository {
    private List<Note> notes = new ArrayList<>();
    @Override
    public List<Note> NotesList(int userId) {
        //noinspection SqlResolve
        String sql = "SELECT * FROM notes WHERE id = ?";

        try (Connection connection = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                notes.add(map(result));
            }
            return notes;
        } catch (SQLException e) {
            ErrorLogger.log("Failed to fetch notes for user", e);
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
            ErrorLogger.log("Failed to fetch notes for user", e);
        }
        return null;
    }

    @Override
    public void save(Note note) {
        //noinspection SqlResolve
        String sql = "INSERT INTO notes (user_id, title, content, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, note.getUsernameId());
            stmt.setString(2, note.getTitle());
            stmt.setString(3, note.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(note.getCreated()));
            stmt.setTimestamp(5, Timestamp.valueOf(note.getUpdated()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLogger.log("Failed to save note", e);
        }
    }

    @Override
    public void update(Note note) {
        //noinspection SqlResolve
        String sql = "UPDATE notes SET title = ?, content = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            stmt.setTimestamp(3, Timestamp.valueOf(note.getUpdated()));
            stmt.setInt(4, note.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLogger.log("Failed to update note", e);
        }
    }

    @Override
    public void delete(int id) {
        //noinspection SqlResolve
        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = mySqlConnectionFactory.getSqlConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLogger.log("Failed to delete note", e);
        }

    }

    private Note map (ResultSet result) throws SQLException {
        return new Note(
                result.getInt("id"),
                result.getInt("user_id"),
                result.getString("title"),
                result.getString("content"),
                result.getString("createAt"),
                result.getString("updatedAt")
        );
    }
}
