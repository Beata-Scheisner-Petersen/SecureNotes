package securenotes.model;

import java.time.LocalDateTime;
public class Note {
    private int id;
    private int usernameId;
    private String title;
    private String content;
    private String created;
    private String updated;

    public Note(int id, int usernameId, String title, String content, String created, String updated) {
        this.id = id;
        this.usernameId = usernameId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(int usernameId) {
        this.usernameId = usernameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
