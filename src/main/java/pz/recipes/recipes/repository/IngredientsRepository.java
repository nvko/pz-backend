package pz.recipes.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Ingredient;

import java.util.List;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByName(String name);

    // returns ingredients which name starts with query
    @Query("SELECT i FROM Ingredient i WHERE i.name LIKE CONCAT(:query,'%')")
    List<Ingredient> findAllByQuery(@Param("query") String query);
}
