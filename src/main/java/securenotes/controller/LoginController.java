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
        Exception exception = new Exception();

        loginView.show();

        String username = loginView.getUsernameInput(readInput);
        String password = loginView.getPasswordInput(readInput);

        AuthSession session = service.login(username, password);

        if (session == null || !session.isAuthenticated()) {
            Logger.log(String.format("[%s] login failed with username: %s \n", dateTimeService.FormatDateTime(), username), exception);
            loginView.error(username);
            start(readInput);
        } else {
            Logger.log(String.format("[%s] login success with username: %s \n", dateTimeService.FormatDateTime(), username), exception);
            loginView.success(username);
        }
    }
}
