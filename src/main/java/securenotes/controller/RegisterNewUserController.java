package securenotes.controller;

import securenotes.logging.Logger;
import securenotes.service.AuthService;
import securenotes.service.FormatDateTimeService;
import securenotes.view.RegisterNewUserView;

import java.util.Scanner;
public class RegisterNewUserController {
    private final AuthService service;
    private final RegisterNewUserView register;
    private FormatDateTimeService formatDateTimeService;

    public RegisterNewUserController(AuthService service, RegisterNewUserView register) {
        this.service = service;
        this.register = register;
    }


    public void start(Scanner readInput) {
        Exception exception = new Exception();
        register.show();

        String username = register.getUsernameInput(readInput);
        String password = register.getPasswordInput(readInput);

        if (service.registerNewUser(username, password)) {
            Logger.log(String.format("[%s] success to create user: %s", formatDateTimeService.FormatDateTime(), username), exception);
            register.success(username);
        } else {
            Logger.log(String.format("[%s] fail to create user: %s", formatDateTimeService.FormatDateTime(), username), exception);
            register.error(username);
        }
    }
}
