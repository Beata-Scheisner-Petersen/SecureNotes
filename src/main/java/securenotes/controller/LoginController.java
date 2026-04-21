package securenotes.controller;

import securenotes.logging.ErrorLogger;
import securenotes.model.AuthSession;
import securenotes.service.AuthService;
import securenotes.service.FormatDateTimeService;
import securenotes.view.LoginView;

import java.util.Scanner;
public class LoginController {
    private final AuthService service;
    private final LoginView loginView;
    private FormatDateTimeService dateTimeService;

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
            ErrorLogger.log(String.format("[%s] login failed with username: %s \n", dateTimeService.FormatDateTime(), username), exception);
            loginView.error(username);
            start(readInput);
        } else {
            ErrorLogger.log(String.format("[%s] login success with username: %s \n", dateTimeService.FormatDateTime(), username), exception);
            loginView.success(username);
        }
    }
}
