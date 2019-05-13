package pz.recipes.recipes.fridge.dto;

import pz.recipes.recipes.domain.Fridge;

import java.util.List;

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
}
