package pz.recipes.recipes.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.IngredientsRepository;
import pz.recipes.recipes.repository.RecipesRepository;
import pz.recipes.recipes.repository.UsersRepository;

import java.util.List;

@Service
public class SearchService {

    @Autowired RecipesRepository recipesRepository;
    @Autowired IngredientsRepository ingredientsRepository;
    @Autowired UsersRepository usersRepository;

    public List<Ingredient> findAllIngredientsByQuery(String query) {
        return ingredientsRepository.findAllByQuery(query);
    }

    public List<Recipe> findAllByUser(String username) {
        User user = usersRepository.findByUsername(username);
        return recipesRepository.findAllByUser(user);
    }

    public List<Recipe> findByQuery(int page, int limit, String sort, String query) {
        return recipesRepository.findAllByQuery(query);
    }
}
