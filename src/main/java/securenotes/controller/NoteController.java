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

    public NoteController(NoteService service, NoteListView listView, NoteEditorView editorView, User user) {
        this.service = service;
        this.listView = listView;
        this.editorView = editorView;
        this.user = user;
    }

    public void showNotes(Scanner readInput) {
        while (true) {
            List<Note> notes = service.getNotesForUser(user.getId());
            listView.showListOfNotes(notes);
            String choice = listView.showMenu(readInput);

            switch (choice) {
                case "1" -> createNote(readInput);
            }
        }
    }

    private void createNote(Scanner readInput) {

        if (service.createNote(user.getId(), editorView.askTitle(readInput), editorView.askContent(readInput))) {
            new ConfirmationView().showSuccess("Note saved");
        } else {
            new ConfirmationView().showError("failed to save note");
        }
    }

    private void editNote(Scanner readInput) {
        int id = listView.askForNoteById(readInput);
        readInput.nextLine();
    }
}
