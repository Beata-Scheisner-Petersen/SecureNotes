package securenotes.view;

import java.util.Scanner;
public class RegisterNewUserView {
    private final GetUsernameAndPasswordView credentials = new GetUsernameAndPasswordView();
    private final ConfirmationView confirmation = new ConfirmationView();

    public void show () {
        System.out.println("=== Register new user ===");
    }

    public String getUsernameInput(Scanner readInput) {
        return credentials.askForUsername(readInput);
    }

    public String getPasswordInput(Scanner readInput) {
        return credentials.askForPassword(readInput);
    }

    public void success(String username) {
        confirmation.showSuccess(String.format("""
                        You success to create a new user with username: %s
                        ------------------------------------------------------------------
                        """, username));
    }

    public void error(String username) {
        confirmation.showError(String.format("""
                        You failed to create a new user with username: %s
                        ------------------------------------------------------------------
                        """, username));
    }
}
