package pz.recipes.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.User;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByUser(User user);

}
