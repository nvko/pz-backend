package pz.recipes.recipes.fridge.dto;

import pz.recipes.recipes.domain.Ingredient;

import java.util.Set;

public class FridgeRequest {

    private Set<Ingredient> ingredients;

    public FridgeRequest(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
