package pz.recipes.recipes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    @JsonIgnore
    private String email;

    @NotEmpty
    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    List<Role> roles;

    private Boolean vege;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Fridge> fridge;

    @NotEmpty
    private String avatarPath;

    public User(@NotEmpty String username, @NotEmpty String email, @NotEmpty String password, List<Role> roles, boolean vege, @NotEmpty String avatarPath) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.vege = vege;
        this.avatarPath = avatarPath;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVege() {
        return vege;
    }

    public void setVege(Boolean vege) {
        this.vege = vege;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(vege, user.vege) &&
                Objects.equals(fridge, user.fridge) &&
                Objects.equals(avatarPath, user.avatarPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, roles, vege, fridge, avatarPath);
    }
}
