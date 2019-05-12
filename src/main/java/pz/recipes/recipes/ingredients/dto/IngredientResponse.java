package pz.recipes.recipes.ingredients.dto;

import pz.recipes.recipes.domain.Ingredient;

import java.util.List;

public class IngredientResponse {

    private List<Ingredient> ingredients;

    public IngredientResponse(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
