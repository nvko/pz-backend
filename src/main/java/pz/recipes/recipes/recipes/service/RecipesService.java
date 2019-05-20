package pz.recipes.recipes.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.RecipeIngredients;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.ingredients.service.IngredientsService;
import pz.recipes.recipes.recipes.dto.RecipesRequest;
import pz.recipes.recipes.repository.RecipeIngredientsRepository;
import pz.recipes.recipes.repository.RecipesRepository;
import pz.recipes.recipes.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RecipesService {

    @Autowired RecipesRepository recipeRepository;
    @Autowired UsersRepository userRepository;
    @Autowired RecipeIngredientsRepository recipeIngredientsRepository;
    @Autowired IngredientsService ingredientService;

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public List<Recipe> getRecipes(int page, int limit, String sort) {
        return recipeRepository.findAll(PageRequest.of(page, limit, Sort.by(sort))).getContent();
    }

    public void addRecipe(User user, RecipesRequest recipeRequest) {
        Recipe recipe = new Recipe(recipeRequest.getTitle(), recipeRequest.getDescription(), user, recipeRequest.getVege());
        saveRecipeAndItsIngredients(recipeRequest, recipe);
    }

    @Transactional
    public void updateRecipe(RecipesRequest recipeRequest, Long id) {
        Recipe recipe = findById(id);
        if (recipe.getId() != null) {
            recipe.setTitle(recipeRequest.getTitle());
            recipe.setDescription(recipeRequest.getDescription());
            recipe.setVege(recipeRequest.getVege());
            recipeIngredientsRepository.deleteAllByRecipe(recipe);
            saveRecipeAndItsIngredients(recipeRequest, recipe);
        }
    }

    private void saveRecipeAndItsIngredients(RecipesRequest recipeRequest, Recipe recipe) {
        recipeRepository.save(recipe);
        for (Ingredient ingredient : recipeRequest.getIngredients()) {
            RecipeIngredients recipeIngredients = new RecipeIngredients(recipe, ingredientService.findById(ingredient.getId()));
            recipeIngredientsRepository.save(recipeIngredients);
        }
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = findById(id);
        recipeRepository.delete(recipe);
    }
}
