package pz.recipes.recipes.recipes.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.RecipesRepository;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RecipeServiceTest {

    @InjectMocks
    RecipesService recipeService = new RecipesService();
    @Mock
    RecipesRepository recipeRepository;


    User user;

    @Before
    public void setUp() {
        user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "default.jpg");

    }

    @Test
    public void findById() {
        Recipe recipe = spy(new Recipe("title", "description", user, true, null));
        Long id = 12L;
        when(recipeRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(recipe));
        Recipe byId = recipeService.findById(id);
        assertEquals(recipe, byId);
    }

    @Test
    public void deleteRecipe() {
        Long id = 3L;
        Recipe recipe = spy(new Recipe("title", "description", user, true, null));
        when(recipeRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(recipe));
        recipeService.deleteRecipe(id);
        verify(recipeRepository,times(1)).delete(recipe);
    }
}