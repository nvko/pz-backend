package pz.recipes.recipes.account.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountRequest {

    private String email;
    private String password;
    private String confirmPassword;

    public AccountRequest(String email) {
        this.email = email;
    }

    public AccountRequest(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
