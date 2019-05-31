package pz.recipes.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.RecipeIngredients;

import java.util.List;

@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredients, Long> {

    List<RecipeIngredients> findAllByRecipe(Recipe recipe);

    void deleteAllByRecipe(Recipe recipe);

    List<RecipeIngredients>  findAllByIngredient(Ingredient ingredient);
}
