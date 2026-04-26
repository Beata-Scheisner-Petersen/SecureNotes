package securenotes.controller;

import securenotes.model.User;
import securenotes.service.AuthService;
import securenotes.view.LoginView;
import java.util.Scanner;

public class LoginController {
    private final AuthService service;
    private final LoginView loginView;
    private User loggedInUser;

    public LoginController(AuthService authService, LoginView view) {
        this.service = authService;
        this.loginView = view;
    }

    public void start(Scanner readInput) {

        loginView.show();

        String username = loginView.getUsernameInput(readInput).trim();
        String password = loginView.getPasswordInput(readInput).trim();

        loggedInUser = service.login(username, password);

        if (loggedInUser != null) {
            loginView.success(loggedInUser.getUsername());
        }

        loginView.error();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
