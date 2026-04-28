package securenotes.view;

import java.util.Scanner;

public class ChangePasswordView {
    private final GetUsernameAndPasswordView credentials = new GetUsernameAndPasswordView();
    private final ConfirmationView confirmationView = new ConfirmationView();

    public void show() {
        System.out.println("=== Change password ===");
    }

    public String getPasswordInput(Scanner readInput) {
        return credentials.askForPassword(readInput).trim();
    }
    public String getNewPasswordInput(Scanner readInput) {
        return credentials.askForNewPassword(readInput).trim();
    }


    public void success(String username) {
        confirmationView.showSuccess("Password change");
    }

    public void error() {
        confirmationView.showError("Password failed to change");
    }
}
