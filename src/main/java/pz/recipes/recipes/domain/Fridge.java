package pz.recipes.recipes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fridge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne()
    @JsonIgnore
    private User user;

    @ManyToOne()
    private Ingredient ingredient;

    public Fridge(User user, Ingredient ingredient) {
        this.user = user;
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fridge fridge = (Fridge) o;
        return Objects.equals(id, fridge.id) &&
                Objects.equals(user, fridge.user) &&
                Objects.equals(ingredient, fridge.ingredient);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, ingredient);
    }
}
