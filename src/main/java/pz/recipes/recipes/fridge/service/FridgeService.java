package pz.recipes.recipes.fridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Fridge;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.FridgeRepository;
import pz.recipes.recipes.repository.IngredientsRepository;
import pz.recipes.recipes.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FridgeService {

    @Autowired FridgeRepository fridgeRepository;
    @Autowired IngredientsRepository ingredientRepository;
    @Autowired UsersRepository usersRepository;

    public List<Fridge> findByUser(User user) {
        return fridgeRepository.findAllByUser(user);
    }

    public void addIngredient(Ingredient ingredient, User user) {
        if (ingredientRepository.findById(ingredient.getId()).isPresent()) {
            Fridge fridge = new Fridge(user, ingredient);
            fridgeRepository.save(fridge);
        }
    }

    public void deleteIngredient(Ingredient ingredient, User user) {
        Fridge fridge = fridgeRepository.findByUserAndIngredient(user, ingredient);
        if (fridge != null) {
            fridgeRepository.delete(fridge);
        }
    }

    public boolean hasIngredient(Ingredient ingredient, User user) {
        return fridgeRepository.findByUserAndIngredient(user, ingredient) != null;
    }

    @Transactional
    public void clearFridge(User user) {
        fridgeRepository.deleteAllByUser(user);
    }
}
