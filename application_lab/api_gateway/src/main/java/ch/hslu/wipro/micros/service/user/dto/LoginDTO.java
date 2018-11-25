package ch.hslu.wipro.micros.service.user.dto;

public class LoginDTO {

    private String userId;
    private String password;

    public LoginDTO() {
        userId = null;
        password = null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
