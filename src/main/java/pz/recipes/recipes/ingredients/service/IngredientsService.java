package pz.recipes.recipes.ingredients.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.ingredients.dto.IngredientsRequest;
import pz.recipes.recipes.repository.IngredientsRepository;

@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepository ingredientRepository;

    public void addIngredient(String name, boolean isVege) {
        ingredientRepository.save(new Ingredient(name, isVege));
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).get();
    }

    public boolean hasIngredient(Long id) {
        return findById(id) != null;
    }

    public boolean hasIngredient(String name) {
        return ingredientRepository.findByName(name).size() != 0;
    }

    public void deleteIngredient(Long id) {
        Ingredient ingredient = findById(id);
        if(ingredient != null) {
            ingredientRepository.delete(ingredient);
        }
    }

    public void updateIngredient(Long id, IngredientsRequest ingredientRequest) {
        Ingredient ingredient = findById(id);
        ingredient.setName(ingredientRequest.getName());
        ingredient.setVege(ingredientRequest.getVege());
        ingredientRepository.save(ingredient);
    }
}
