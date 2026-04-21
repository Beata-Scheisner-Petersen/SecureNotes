package securenotes.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class FormatDateTimeService {

    public String FormatDateTime () {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm"));
    }
}
