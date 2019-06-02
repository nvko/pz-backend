package pz.recipes.recipes.recipes.dto;

import pz.recipes.recipes.domain.Recipe;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipesResponse that = (RecipesResponse) o;
        return Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes);
    }
}
