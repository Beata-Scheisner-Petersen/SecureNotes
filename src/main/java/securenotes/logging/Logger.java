package securenotes.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Logger {
    private static final String LOG_FILE = "src/main/resources/logs/error.log";

    public static synchronized void log(String message, Exception e) {

        try(FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write("[" + getFormatedDateTime() + "] ");
            writer.write(message + "\n");

            if (e != null) {
                writer.write("Exception: " + e.getClass().getName() + "\n");
                writer.write("Message: " + e.getMessage() + "\n\n");
            }
        } catch (IOException IO_Ex) {
            System.err.println("Failed to write to log file: " + IO_Ex.getMessage());
        }
    }
    private static String getFormatedDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTimeFormatter.format(localDateTime);
    }

    private void createErrorTextFileIfNotExist(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.out.println("Error: File couldn't creates!");
        }
    }
}
