package securenotes.controller;

import securenotes.model.Note;
import securenotes.model.User;
import securenotes.service.NoteService;
import securenotes.view.Confirmation;
import securenotes.view.NoteEditorView;
import securenotes.view.NoteListView;
import java.util.List;
import java.util.Scanner;

public class NoteController {
    private final NoteService service;
    private final NoteListView listView;
    private final NoteEditorView editorView;
    private final User user;
    private final Confirmation confirmation = new Confirmation();

    public NoteController(NoteService service, NoteListView listView, NoteEditorView editorView, User user) {
        this.service = service;
        this.listView = listView;
        this.editorView = editorView;
        this.user = user;
    }

    public void showNotes(Scanner readInput) {
        while (true) {
            List<Note> notes = service.getNotesForUser(user.getId(), user.getRole());
            listView.showListOfNotes(notes);
            String choice = listView.showMenu(readInput, user.getRole());

            switch (choice) {
                case "1" -> createNote(readInput);
                case "2" -> editNote(readInput);
                case "3" -> deleteNote(readInput);
                case "4" -> {return;}

                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void createNote(Scanner readInput) {

        if (service.createNote(user.getId(), editorView.askTitle(readInput), editorView.askContent(readInput), user.getRole())) {
            confirmation.showSuccess("Note saved");
        } else {
            confirmation.showError("failed to save note");
        }
    }

    private void editNote(Scanner readInput) {
        int id = listView.askForNoteId(readInput);
        String title = editorView.askTitle(readInput);
        String content = editorView.askContent(readInput);

        if (service.updateNote(id, title, content, user.getRole())) {
            confirmation.showSuccess("Note updated");
        } else {
            confirmation.showError("failed to update note");
        }
    }

    private void deleteNote(Scanner readInput) {
        int id = listView.askForNoteId(readInput);

        if (service.deleteNote(id)) {
            confirmation.showSuccess("Note deleted");
        } else {
            confirmation.showError("Failed to delete note");
        }
    }
}
