package pz.recipes.recipes.register.dto;

public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
