package pz.recipes.recipes.ingredients.controller;

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
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.ingredients.dto.IngredientsRequest;
import pz.recipes.recipes.ingredients.service.IngredientsService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class IngredientsControllerTest {

    @InjectMocks
    IngredientsController ingredientsController = new IngredientsController();
    @Mock
    IngredientsService ingredientService;
    @Mock
    Authentication authentication;

    @Test
    public void addIngredientAdminIngredientExist() {
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("This ingredient already exists"), HttpStatus.CONFLICT);
        IngredientsRequest ingredientRequest = new IngredientsRequest();
        ingredientRequest.setName("ingredient");
        ingredientRequest.setVege(true);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_ADMIN));
        when(ingredientService.hasIngredient(ingredientRequest.getName())).thenReturn(true);
        ResponseEntity<?> responseEntity = ingredientsController.addIngredient(authentication, ingredientRequest);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void addIngredientAdminIngredientDoesNotExist() {
        IngredientsRequest ingredientRequest = new IngredientsRequest();
        ingredientRequest.setName("ingredient");
        ingredientRequest.setVege(true);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_ADMIN));
        when(ingredientService.hasIngredient(ingredientRequest.getName())).thenReturn(false);
        ResponseEntity<?> responseEntity = ingredientsController.addIngredient(authentication, ingredientRequest);
        verify(ingredientService, times(1)).addIngredient(ingredientRequest.getName(), ingredientRequest.getVege());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addIngredientUser() {
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("You are not authorized to add new ingredients."), HttpStatus.UNAUTHORIZED);
        IngredientsRequest ingredientRequest = new IngredientsRequest();
        ingredientRequest.setName("ingredient");
        ingredientRequest.setVege(true);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_USER));
        ResponseEntity<?> responseEntity = ingredientsController.addIngredient(authentication, ingredientRequest);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void findById() {
        Ingredient ingredient = new Ingredient("ingredient", true);
        when(ingredientService.findById(3L)).thenReturn(ingredient);

        ResponseEntity<Ingredient> expectedResponse = new ResponseEntity<>(ingredient, HttpStatus.OK);

        ResponseEntity<?> responseEntity = ingredientsController.findById(3L);

        assertEquals(expectedResponse, responseEntity);
    }


    @Test
    public void deleteIngredientAdminIngredientExist() {
        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getId()).thenReturn(12L);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_ADMIN));
        when(ingredientService.hasIngredient(ingredient.getId())).thenReturn(true);
        ResponseEntity<?> responseEntity = ingredientsController.deleteIngredient(authentication, ingredient.getId());
        verify(ingredientService,times(1)).deleteIngredient(ingredient.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void deleteIngredientAdminIngredientDoesNotExist() {
        Ingredient ingredient = mock(Ingredient.class);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("This ingredient doesn't exist"), HttpStatus.CONFLICT);
        when(ingredient.getId()).thenReturn(12L);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_ADMIN));
        when(ingredientService.hasIngredient(ingredient.getId())).thenReturn(false);
        ResponseEntity<?> responseEntity = ingredientsController.deleteIngredient(authentication, ingredient.getId());
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void deleteIngredientUser() {
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("You are not authorized to delete new ingredients."), HttpStatus.UNAUTHORIZED);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_USER));
        ResponseEntity<?> responseEntity = ingredientsController.deleteIngredient(authentication,2L);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void updateIngredientAdminIngredientExist() {
        Ingredient ingredient = mock(Ingredient.class);
        IngredientsRequest ingredientRequest = new IngredientsRequest();
        ingredientRequest.setName("ingredient");
        ingredientRequest.setVege(true);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_ADMIN));
        when(ingredient.getId()).thenReturn(12L);
        when(ingredientService.hasIngredient(ingredient.getId())).thenReturn(true);
        ResponseEntity<?> responseEntity = ingredientsController.updateIngredient(authentication, ingredient.getId(), ingredientRequest);
        verify(ingredientService,times(1)).updateIngredient(ingredient.getId(), ingredientRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateIngredientAdminIngredientDoesNotExist() {
        Ingredient ingredient = mock(Ingredient.class);
        IngredientsRequest ingredientRequest = new IngredientsRequest();
        ingredientRequest.setName("ingredient");
        ingredientRequest.setVege(true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("This ingredient doesn't exist"), HttpStatus.CONFLICT);
        when(ingredient.getId()).thenReturn(12L);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_ADMIN));
        when(ingredientService.hasIngredient(ingredient.getId())).thenReturn(false);
        ResponseEntity<?> responseEntity = ingredientsController.updateIngredient(authentication, ingredient.getId(), ingredientRequest);
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void updateIngredientUser() {
        IngredientsRequest ingredientRequest = new IngredientsRequest();
        ingredientRequest.setName("ingredient");
        ingredientRequest.setVege(true);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("You are not authorized to update ingredients."), HttpStatus.UNAUTHORIZED);
        when((List<Role>) authentication.getAuthorities()).thenReturn(Collections.singletonList(Role.ROLE_USER));
        ResponseEntity<?> responseEntity = ingredientsController.updateIngredient(authentication,2L, ingredientRequest);
        assertEquals(expectedResponse, responseEntity);
    }
}