package securenotes.view;

import java.util.Scanner;

public class GetUsernameAndPasswordView {

    public String askForUsername(Scanner readInput) {
        System.out.print("Username: ");
        return readInput.nextLine();
    }

    public String askForPassword(Scanner readInput) {
        System.out.print("Password: ");
        return readInput.nextLine();
    }

    public String askForNewPassword(Scanner readInput) {
        System.out.print("New password: ");
        return readInput.nextLine();
    }
}
