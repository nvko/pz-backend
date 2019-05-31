package pz.recipes.recipes.fridge.dto;

import pz.recipes.recipes.domain.Fridge;

import java.util.List;
import java.util.Objects;

public class FridgeResponse {

    private List<Fridge> ingredients;

    public FridgeResponse(List<Fridge> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Fridge> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Fridge> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FridgeResponse that = (FridgeResponse) o;
        return Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ingredients);
    }
}
