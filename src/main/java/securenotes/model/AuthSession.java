package securenotes.model;

public class AuthSession {
    private int userId;
    private String username;
    private String loginTime;
    private boolean authenticated;

    public AuthSession() {
        this.authenticated = false;
    }

    public AuthSession(int userId, String username, String loginTime) {
        this.userId = userId;
        this.username = username;
        this.loginTime = loginTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getRole() {
        return role;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void logout() {
        this.authenticated = false;
        this.userId = 0;
        this.username = null;
        this.loginTime = null;
    }
}
