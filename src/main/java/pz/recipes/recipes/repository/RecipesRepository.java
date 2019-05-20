package pz.recipes.recipes.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Recipe;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByTitle(String title);


}
