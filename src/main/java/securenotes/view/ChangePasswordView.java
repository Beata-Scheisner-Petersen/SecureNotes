package securenotes.view;

import java.util.Scanner;

public class ChangePasswordView {
    private final GetUsernameAndPasswordView credentials = new GetUsernameAndPasswordView();
    private final Confirmation confirmation = new Confirmation();

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
        confirmation.showSuccess("Password change");
    }

    public void error() {
        confirmation.showError("Password failed to change");
    }
}
