package securenotes.view;

import java.util.Scanner;

public class GetUsernameAndPasswordView {

    public String askForUsername(Scanner readInput) {
        System.out.print("Username: ");
        return readInput.nextLine().trim();
    }

    public String askForPassword(Scanner readInput) {
        System.out.print("Password: ");
        return readInput.nextLine().trim();
    }

    public String askForNewPassword(Scanner readInput) {
        System.out.print("New password: ");
        return readInput.nextLine().trim();
    }
}
