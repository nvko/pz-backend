package pz.recipes.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Ingredient;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

//    @Query("SELECT i FROM Ingredient i WHERE name LIKE :query")
//    List<Ingredient> findByQuery(@Param("query") String query);

    List<Ingredient> findByName(String name);

}
