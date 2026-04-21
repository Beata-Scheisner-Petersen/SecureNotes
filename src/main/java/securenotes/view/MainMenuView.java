package securenotes.view;

import java.util.Scanner;
public class MainMenuView {

    public void show() {
        System.out.println("""
                -------------------------------------------------------------------------
                === Welcome ===
                1. Login
                2. Create new user
                3. exit
                """);
    }

    public String getChoice(Scanner readInput) {
        System.out.print("Choose an alternative: ");
        String choice = readInput.nextLine();
        System.out.println("---------------------------------------------------------------");
        return choice;
    }
}
