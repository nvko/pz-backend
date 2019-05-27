package pz.recipes.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Ingredient;

import java.util.List;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

//    @Query("SELECT i FROM Ingredient i WHERE name LIKE :query")
//    List<Ingredient> findByQuery(@Param("query") String query);

    List<Ingredient> findByName(String name);


    @Query("SELECT i FROM ingredient i WHERE i.name LIKE CONCAT('%',:query,'%')")
    List<Ingredient> findAllByQuery(String query);
}
