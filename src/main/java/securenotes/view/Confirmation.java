package securenotes.view;

public class Confirmation {

    public void showError(String message) {
        System.out.printf("""
                Error: %s
                -------------------------------------------------------------------
                """, message);
    }

    public void showSuccess(String message) {
        System.out.printf("""
                Success: %s
                -------------------------------------------------------------------
                """, message);
    }
}
