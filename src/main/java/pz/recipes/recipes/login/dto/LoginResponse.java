package pz.recipes.recipes.login.dto;

public class LoginResponse {

    private Long userID;
    private String token;

    public LoginResponse(Long userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
