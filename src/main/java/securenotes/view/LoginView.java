package securenotes.view;

import java.util.Scanner;

public class LoginView {
    private final GetUsernameAndPasswordView credentials = new GetUsernameAndPasswordView();
    private final Confirmation confirmation = new Confirmation();

    public void show() {
        System.out.println("=== Login ===");
    }

    public String getUsernameInput(Scanner readInput) {
        return credentials.askForUsername(readInput).trim();
    }

    public String getPasswordInput(Scanner readInput) {
        return credentials.askForPassword(readInput).trim();
    }

    public void success(String username) {
        confirmation.showSuccess(String.format("You success to login with username: %s", username));
    }

    public void error() {
        confirmation.showError("You failed to login: wrong username or password!");
    }
}
