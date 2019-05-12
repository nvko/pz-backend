package pz.recipes.recipes.ingredients.dto;

public class IngredientRequest {

    private String name;
    private Boolean isVege;

    public String getName() {
        return name;
    }

    public Boolean getVege() {
        return isVege;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVege(boolean vege) {
        isVege = vege;
    }
}
