package pz.recipes.recipes.recipes.controller;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.recipes.dto.RecipesRequest;
import pz.recipes.recipes.recipes.dto.RecipesResponse;
import pz.recipes.recipes.recipes.service.RecipesService;
import pz.recipes.recipes.users.service.UsersService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RecipeControllerTest {

    @InjectMocks
    RecipesController recipeController = new RecipesController();
    @Mock
    RecipesService recipeService;
    @Mock
    UsersService userService;
    @Mock
    Authentication authentication;

    @Test
    public void getRecipes() {
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new RecipesResponse(Lists.emptyList()), HttpStatus.OK);
        ResponseEntity<?> responseEntity = recipeController.getRecipes(10, 10, "id");
        verify(recipeService, times(1)).getRecipes(10, 10, "id");
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void getRecipe() {
        Long id = 12L;
        User user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true, "default.jpg");
        Recipe recipe = new Recipe("recipeTitle", "recipeDescription", user, true, null);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(recipe, HttpStatus.OK);
        when(recipeService.findById(id)).thenReturn(recipe);
        ResponseEntity<?> responseEntity = recipeController.getRecipe(id);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void addRecipeHasSomethingNull() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("Request is invalid"), HttpStatus.CONFLICT);
        ResponseEntity responseEntity = recipeController.addRecipe(authentication, recipeRequest);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void addRecipeUserExist() {
        User user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true, "default.jpg");
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(false);
        when(userService.findByUsername(authentication.getName())).thenReturn(user);
        ResponseEntity responseEntity = recipeController.addRecipe(authentication, recipeRequest);
        verify(recipeService, times(1)).addRecipe(user, recipeRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addRecipeUserDoNotExist() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(false);
        when(userService.findByUsername(authentication.getName())).thenReturn(null);
        ResponseEntity responseEntity = recipeController.addRecipe(authentication, recipeRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void updateRecipeHasSomethingNull() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("Request is invalid"), HttpStatus.CONFLICT);
        ResponseEntity responseEntity = recipeController.updateRecipe(authentication, 12L, recipeRequest);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void updateRecipeUserExist() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        configStubs(13, 13, recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.updateRecipe(authentication, 13L, recipeRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateRecipeUserDoNotExist() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        configStubs(12, 16, recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.updateRecipe(authentication, 12L, recipeRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }


    @Test
    public void correctDeleteRecipe() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        configStubs(12L, 12L, recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.deleteRecipe(authentication, 12L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void incorrectDeleteRecipe() {
        RecipesRequest recipeRequest = mock(RecipesRequest.class);
        configStubs(12L, 13L, recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.deleteRecipe(authentication, 12L);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    public void configStubs(long idFirstUser, long idSecondUser, RecipesRequest recipeRequest) {
        User user = spy(new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true, "default.jpg"));
        User anotherUser = spy(new User("User2", "email2@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true, "default.jpg"));
        Recipe recipe = spy(new Recipe("recipeTitle", "recipeDescription", user, true, null));
        when(user.getId()).thenReturn(idFirstUser);
        when(anotherUser.getId()).thenReturn(idSecondUser);
        when(recipeRequest.hasSomethingNull()).thenReturn(false);
        when(recipeService.findById(idFirstUser)).thenReturn(recipe);
        when(userService.findByUsername(authentication.getName())).thenReturn(anotherUser);
    }
}