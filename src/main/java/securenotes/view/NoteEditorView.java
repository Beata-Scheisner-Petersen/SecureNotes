package securenotes.view;

import java.util.Scanner;

public class NoteEditorView {
    public String askTitle(Scanner readInput) {
        System.out.print("Title: ");
        return readInput.nextLine();
    }

    public String askContent(Scanner readInput) {
        System.out.print("Content: ");
        return readInput.nextLine();
    }
}
