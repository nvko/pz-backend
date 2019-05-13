package pz.recipes.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Fridge;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.User;

import java.util.List;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

     List<Fridge> findAllByUser(User user);

     Fridge findByUserAndIngredient(User user, Ingredient ingredient);

     Fridge findByIngredient(Ingredient ingredient);

     void deleteAllByUser(User user);
}
