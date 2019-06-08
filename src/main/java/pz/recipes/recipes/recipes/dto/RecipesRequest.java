package pz.recipes.recipes.recipes.dto;

import pz.recipes.recipes.domain.Ingredient;

import java.util.Set;

public class RecipesRequest {

    private String title;
    private String description;
    private Boolean vege;
    private String imgPath;
    private Set<Ingredient> ingredients;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVege() {
        return vege;
    }

    public void setVege(Boolean vege) {
        this.vege = vege;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean hasSomethingNull() {
        return getTitle() == null || getDescription() == null || getVege() == null || getIngredients() == null;
    }
}
