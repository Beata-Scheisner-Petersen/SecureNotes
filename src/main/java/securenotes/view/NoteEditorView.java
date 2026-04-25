package securenotes.view;

import java.util.Scanner;
public class NoteEditorView {
    public String askTitle(Scanner readInput) {
        System.out.print("Titel: ");
        return readInput.nextLine();
    }

    public String askContent(Scanner readInput) {
        System.out.print("Innehåll: ");
        return readInput.nextLine();
    }
}
