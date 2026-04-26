package securenotes;

import securenotes.controller.LoginController;
import securenotes.controller.MainMenuController;
import securenotes.controller.RegisterNewUserController;
import securenotes.repository.interfaces.INoteRepository;
import securenotes.repository.interfaces.IUserRepository;
import securenotes.repository.mysql.mySqlNoteRepository;
import securenotes.repository.mysql.mySqlUserRepository;
import securenotes.service.AuthService;
import securenotes.service.NoteService;
import securenotes.view.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner readInput = new Scanner(System.in);

        // --- Repositories ---
        IUserRepository userRepository = new mySqlUserRepository();
        INoteRepository noteRepository = new mySqlNoteRepository();

        // --- Services ---
        AuthService authService = new AuthService(userRepository);
        NoteService noteService = new NoteService(noteRepository);

        // --- Views ---
        LoginView loginView = new LoginView();
        RegisterNewUserView registerView = new RegisterNewUserView();
        MainMenuView mainMenuView = new MainMenuView();
//        NoteListView noteListView = new NoteListView();
//        NoteEditorView noteEditorView = new NoteEditorView();

        // --- Controllers ---
        LoginController loginController = new LoginController(authService, loginView);
        RegisterNewUserController registerController = new RegisterNewUserController(authService, registerView);

        // NoteController skapas först när användaren loggat in
        // så vi skickar in noteService + views senare

        MainMenuController mainMenuController =
                new MainMenuController(mainMenuView, loginController, registerController/*, noteService, noteListView, noteEditorView*/);

        // --- Starta programmet ---
        mainMenuController.start(readInput);

        readInput.close();
    }
}
