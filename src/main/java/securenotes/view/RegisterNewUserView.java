package securenotes.view;

import java.util.Scanner;

public class RegisterNewUserView {
    private final GetUsernameAndPasswordView credentials = new GetUsernameAndPasswordView();
    private final ConfirmationView confirmationView = new ConfirmationView();

    public void show () {
        System.out.println("=== Register new user ===");
    }

    public String getUsernameInput(Scanner readInput) {
        return credentials.askForUsername(readInput).trim();
    }

    public String getPasswordInput(Scanner readInput) {
        return credentials.askForPassword(readInput).trim();
    }

    public void success(String username) {
        confirmationView.showSuccess(String.format("an account with username: %s is created", username));
    }

    public void error(String username) {
        confirmationView.showError(String.format("Failed to create a new account with username: %s", username));
    }
}
