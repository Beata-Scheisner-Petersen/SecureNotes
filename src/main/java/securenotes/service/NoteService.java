package securenotes.service;

import securenotes.logging.ErrorLogger;
import securenotes.model.Note;
import securenotes.repository.interfaces.INoteRepository;

import java.util.List;
public class NoteService {
    private final INoteRepository noteRepository;
    private FormatDateTimeService dateTimeService;

    public NoteService(INoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesForUser(int userId) {
        return noteRepository.NotesList(userId);
    }

    public Note getNoteByID(int id) {
        return noteRepository.findById(id);
    }

    public boolean createNote(int userId, String title, String content) {
        Note note = null;
        note = new Note(0, userId, title, content, dateTimeService.FormatDateTime(), null);
        if (note.getContent().isBlank() || note.getCreated().isBlank()) {
            return false;
        } else {
            noteRepository.save(note);
            return true;
        }
    }



    public void updateNote(Note note, String newTitle, String newContent) {
        if (newTitle.isBlank()) {
            note.setTitle(note.getTitle());
        } else {
            note.setTitle(newTitle);
        }

        if (newContent.isBlank()) {
            note.setContent(note.getContent());
        } else {
            note.setContent(newContent);
        }

        note.setUpdated(dateTimeService.FormatDateTime());

        try {
            noteRepository.update(note);
        } catch (Exception e) {
            System.out.println("Failed to update Note");
            ErrorLogger.log("failed to update note", e);
        }
    }

    public void deleteNote(int noteId) {
        try {
            noteRepository.delete(noteId);
        } catch (Exception e) {
            System.out.println("failed to delete note");
            ErrorLogger.log("Failed to delete note", e);
        }
    }
}
