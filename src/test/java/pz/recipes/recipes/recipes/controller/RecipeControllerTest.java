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
import pz.recipes.recipes.recipes.dto.RecipeRequest;
import pz.recipes.recipes.recipes.dto.RecipeResponse;
import pz.recipes.recipes.recipes.service.RecipeService;
import pz.recipes.recipes.users.service.UserService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController = new RecipeController();
    @Mock
    RecipeService recipeService;
    @Mock
    UserService userService;
    @Mock
    Authentication authentication;

    @Test
    public void getRecipes() {
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new RecipeResponse(Lists.emptyList()), HttpStatus.OK);
        ResponseEntity<?> responseEntity = recipeController.getRecipes(10, 10, "id");
        verify(recipeService, times(1)).getRecipes(10, 10, "id");
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void getRecipe() {
        Long id=12L;
        User user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true);
        Recipe recipe = new Recipe("recipeTitle", "recipeDescription", user, true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(recipe, HttpStatus.OK);
        when(recipeService.findById(id)).thenReturn(recipe);
        ResponseEntity<?> responseEntity = recipeController.getRecipe(id);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void addRecipeHasSomethingNull() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("Request is invalid"), HttpStatus.CONFLICT);
        ResponseEntity responseEntity = recipeController.addRecipe(authentication, recipeRequest);
        assertEquals(expectedResponse,responseEntity);
    }

    @Test
    public void addRecipeUserExist() {
        User user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true);
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(false);
        when(userService.findByUsername(authentication.getName())).thenReturn(user);
        ResponseEntity responseEntity = recipeController.addRecipe(authentication, recipeRequest);
        verify(recipeService,times(1)).addRecipe(user,recipeRequest);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
    @Test
    public void addRecipeUserDoNotExist() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(false);
        when(userService.findByUsername(authentication.getName())).thenReturn(null);
        ResponseEntity responseEntity = recipeController.addRecipe(authentication, recipeRequest);
        assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
    }

    @Test
    public void updateRecipeHasSomethingNull() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        when(recipeRequest.hasSomethingNull()).thenReturn(true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("Request is invalid"), HttpStatus.CONFLICT);
        ResponseEntity responseEntity = recipeController.updateRecipe(authentication,12L, recipeRequest);
        assertEquals(expectedResponse,responseEntity);
    }

    @Test
    public void updateRecipeUserExist() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        configStubs(13, 13,recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.updateRecipe(authentication, 13L, recipeRequest);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
    @Test
    public void updateRecipeUserDoNotExist() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        configStubs(12, 16,recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.updateRecipe(authentication, 12L, recipeRequest);
        assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
    }


    @Test
    public void correctDeleteRecipe() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        configStubs(12L,12L,recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.deleteRecipe(authentication, 12L);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }


    @Test
    public void incorrectDeleteRecipe() {
        RecipeRequest recipeRequest = mock(RecipeRequest.class);
        configStubs(12L,13L,recipeRequest);
        ResponseEntity<?> responseEntity = recipeController.deleteRecipe(authentication, 12L);
        assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
    }

    public void configStubs(long idFirstUser,long idSecondUser,RecipeRequest recipeRequest) {
        User user = spy(new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true));
        User anotherUser = spy(new User("User2", "email2@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true));
        Recipe recipe = spy(new Recipe("recipeTitle", "recipeDescription", user, true));
        when(user.getId()).thenReturn(idFirstUser);
        when(anotherUser.getId()).thenReturn(idSecondUser);
        when(recipeRequest.hasSomethingNull()).thenReturn(false);
        when(recipeService.findById(idFirstUser)).thenReturn(recipe);
        when(userService.findByUsername(authentication.getName())).thenReturn(anotherUser);
    }
}