package pz.recipes.recipes.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.repository.RecipesRepository;

import java.util.List;

@Service
public class SearchService {

   @Autowired RecipesRepository recipeRepository;

//    public List<Recipe> findAllRecipesByQuery(String query, int page, int limit) {
//        return recipeRepository.findAllByQuery(PageRequest.of(page, limit)).getContent();
//        return recipeRepository.findAll(PageRequest.of(page, limit)).getContent();
//    }
}
