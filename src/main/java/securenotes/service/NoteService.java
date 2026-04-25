package securenotes.service;

import securenotes.logging.Logger;
import securenotes.model.Note;
import securenotes.repository.interfaces.INoteRepository;
import java.util.List;

public class NoteService {
    private final INoteRepository noteRepository;

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
        note = new Note(userId, title, content, Logger.getFormatedDateTime(), null);
        if (note.getContent().isBlank() || note.getCreated().isBlank()) {
            return false;
        } else {
            noteRepository.save(note);
            return true;
        }
    }

    public boolean updateNote(int id, String newTitle, String newContent) {
        Note note = noteRepository.findById(id);
        if (note == null) {
            return false;
        }

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

        note.setUpdated(Logger.getFormatedDateTime());

        if (note.getUpdated() == null || note.getUpdated().isBlank()) {
            return false;
        }
        noteRepository.update(note);
        return true;
    }

    public void deleteNote(int noteId) {
        try {
            noteRepository.delete(noteId);
        } catch (Exception e) {
            System.out.println("failed to delete note");
            Logger.log("Failed to delete note", e);
        }
    }
}
