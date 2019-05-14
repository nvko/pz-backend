package pz.recipes.recipes.find.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.repository.RecipeRepository;

import java.util.List;

@Service
public class SearchService {

   @Autowired RecipeRepository recipeRepository;

   public List<Recipe> findRecipesByQuery(String query) {
       return recipeRepository.findByTitle(query);
   }

}
