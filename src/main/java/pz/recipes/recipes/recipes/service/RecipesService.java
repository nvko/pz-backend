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
import java.util.*;

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

    //TODO: sort
    public List<Recipe> findByIngredients(int page, int limit, String sort, Set<Ingredient> ingredients) {
        List<RecipeIngredients> recipeIngredients = new ArrayList<>();
        List<Recipe> finalRecipes = new ArrayList<>();
        Set<Recipe> recipeIngredientsFiltered = new HashSet<>();
        List<Recipe> recipes = new ArrayList<>();

        // przejdź po składnikach requestu i wybierz wszystkie przepisy, które zawierają chociaż jeden składnik
        for (Ingredient ingredient : ingredients) {
            recipeIngredients.addAll(recipeIngredientsRepository.findAllByIngredient(ingredient));
        }
        // wrzuć przepisy do osobnej listy i do setu
        for (RecipeIngredients r : recipeIngredients) {
            recipes.add(r.recipe);
            recipeIngredientsFiltered.add(r.recipe);
        }
        // przeiteruj po secie (żeby się nie powtarzać) i sprawdź, które z przepisów mają tylko pożądane składniki
        for (Recipe r : recipeIngredientsFiltered) {
            int occurencies = Collections.frequency(recipes, r);
//          przepisy, które posiadają tylko te składniki, które wysłalismy
//            if(occurencies == ingredients.size() && r.getIngredients().size() == ingredients.size()) {
//                finalRecipes.add(r);
//            }
//          przepisy, które zawierają w sobie składniki przesłane i inne
            if (occurencies == ingredients.size()) {
                finalRecipes.add(r);
            }
        }
        return finalRecipes;
    }

    public void addRecipe(User user, RecipesRequest recipeRequest) {
        Recipe recipe = new Recipe(recipeRequest.getTitle(), recipeRequest.getDescription(), user, recipeRequest.getVege(), recipeRequest.getImgPath());
        saveRecipeAndItsIngredients(recipeRequest, recipe);
    }

    @Transactional
    public void updateRecipe(RecipesRequest recipeRequest, Long id) {
        Recipe recipe = findById(id);
        if (recipe.getId() != null) {
            recipe.setTitle(recipeRequest.getTitle());
            recipe.setDescription(recipeRequest.getDescription());
            recipe.setVege(recipeRequest.getVege());
            recipe.setImgPath(recipeRequest.getImgPath());
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
