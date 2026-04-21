package securenotes.view;

public class ConfirmationView {

    public String showError(String message) {
        return String.format("""
                Error: %s
                -------------------------------------------------------------------
                """, message);
    }

    public String showSuccess(String message) {
        return String.format("""
                Success: %s
                -------------------------------------------------------------------
                """, message);
    }
}
