package securenotes.view;

import securenotes.model.Note;
import java.util.List;
import java.util.Scanner;

public class NoteListView {

    public void showListOfNotes(List<Note> listOfNotes) {
        System.out.println("=== Notes ===");
        if (listOfNotes == null || listOfNotes.isEmpty()) {
            System.out.println("No notes. \n" +
                    "--------------------------------------------------------------------");
        } else {
            for (Note note : listOfNotes) {
                System.out.printf("%d. %s", note.getId(), note.getTitle());
            }
        }
    }

    public String showMenu(Scanner readInput) {
        System.out.println("""
                1. Create new note
                2. Edit note
                3. Delete note
                4. Logout and return to main menu
                Choice:\s""");
        return readInput.nextLine();
    }

    public int askForNoteId(Scanner readInput) {
        System.out.print("Enter id-number for the note you want to see: ");
        return Integer.parseInt(readInput.nextLine());
    }
}
