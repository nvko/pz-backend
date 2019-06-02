package pz.recipes.recipes.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.User;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByUser(User user, Pageable pageable);

    @Query("SELECT r FROM Recipe r WHERE r.title LIKE CONCAT('%',:query,'%')")
    List<Recipe> findAllByQuery( @Param("query") String query, Pageable pageable);
}
