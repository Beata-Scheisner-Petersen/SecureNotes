package securenotes.controller;

import securenotes.model.User;
import securenotes.service.NoteService;
import securenotes.view.MainMenuView;
import securenotes.view.NoteEditorView;
import securenotes.view.NoteListView;
import java.util.Scanner;

public class MainMenuController {
    private final MainMenuView view;
    private final LoginController loginController;
    private final RegisterNewUserController registerController;
    private final NoteService noteService;
    private final NoteListView listView;
    private final NoteEditorView noteEditorView;

    private  boolean running = true;

    public MainMenuController(MainMenuView view, LoginController loginController, RegisterNewUserController registerController,
                              NoteService noteService, NoteListView noteListView, NoteEditorView noteEditorView ) {
        this.view = view;
        this.loginController = loginController;
        this.registerController = registerController;
        this.noteService = noteService;
        this.listView = noteListView;
        this.noteEditorView = noteEditorView;
    }

    public void start(Scanner readInput) {

        while (running){
            view.show();
            String choice = view.getChoice(readInput);

            switch (choice) {
                case "1" -> loginHandler(readInput);
                case "2" -> registerController.start(readInput);
                case "3" -> running = false;

                default -> {
                    System.out.println("Invalid choice, try again!");
                    start(readInput);
                }
            }
        }
    }

    private void loginHandler(Scanner readInput) {
        loginController.start(readInput);
        User loggedInUser = loginController.getLoggedInUser();

        if (loggedInUser != null) {
            NoteController controller = new NoteController(noteService, listView, noteEditorView, loggedInUser);
            controller.showNotes(readInput);
        }
        start(readInput);
    }
}
