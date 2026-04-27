package securenotes.repository.interfaces;

import securenotes.model.Note;
import java.util.List;

public interface INoteRepository {
    List<Note> notesListByUserId(int userId);
    List<Note> notesForAdmin();
    Note findById(int id);
    void save(Note note);
    void update(Note note);
    void delete(int id);
}
