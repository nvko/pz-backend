package pz.recipes.recipes.recipes.dto;

import pz.recipes.recipes.domain.Recipe;

import java.util.List;

public class RecipesResponse {

    private List<Recipe> recipes;

    public RecipesResponse(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
