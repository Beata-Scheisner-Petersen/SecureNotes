package securenotes.view;

public class ConfirmationView {

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
