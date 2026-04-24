package securenotes.controller;

import securenotes.logging.Logger;
import securenotes.service.AuthService;
import securenotes.view.RegisterNewUserView;
import java.util.Scanner;
public class RegisterNewUserController {
    private final AuthService service;
    private final RegisterNewUserView register;

    public RegisterNewUserController(AuthService service, RegisterNewUserView register) {
        this.service = service;
        this.register = register;
    }


    public void start(Scanner readInput) {
        Exception exception = new Exception();
        register.show();

        String username = register.getUsernameInput(readInput).trim();
        String password = register.getPasswordInput(readInput).trim();

        if (service.registerNewUser(username, password)) {
            Logger.log(String.format("success to create user: %s", username), exception);
            register.success(username);
        } else {
            Logger.log(String.format("fail to create user: %s", username), exception);
            register.error(username);
        }
    }
}
