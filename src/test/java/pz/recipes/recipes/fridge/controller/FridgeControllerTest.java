package pz.recipes.recipes.fridge.controller;

import org.junit.Before;
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
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.fridge.dto.FridgeResponse;
import pz.recipes.recipes.fridge.service.FridgeService;
import pz.recipes.recipes.ingredients.service.IngredientsService;
import pz.recipes.recipes.users.service.UsersService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FridgeControllerTest {

    @InjectMocks
    FridgeController fridgeController = new FridgeController();
    @Mock
    UsersService userService;
    @Mock
    IngredientsService ingredientService;
    @Mock
    FridgeService fridgeService;
    @Mock
    Authentication authentication;

    User user;

    @Before
    public void setUp() {
        user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true, "default.jpg");
    }

    @Test
    public void getFridge() {
        when(authentication.getName()).thenReturn(user.getUsername());

        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<?> fridge = fridgeController.getFridge(authentication);

        verify(authentication, times(1)).getName();
        verify(fridgeService, times(1)).findByUser(user);
        verify(userService, times(1)).findByUsername(authentication.getName());

        assertEquals(HttpStatus.OK, fridge.getStatusCode());
        assertEquals(new FridgeResponse(fridgeService.findByUser(user)), fridge.getBody());
    }

//    @Test
//    public void ingredientAlreadyExistInFridge() {
//        Ingredient ingredient = new Ingredient("skladnik", true);
//        fridgeService.addIngredient(ingredient, user);
//        when(ingredientService.findById(ingredient.getId())).thenReturn(ingredient);
//        when(fridgeService.hasIngredient(ingredient, user)).thenReturn(false);
//
//        ResponseEntity<?> responseEntity = fridgeController.addIngredient(authentication, ingredient.getId());
//
//        assertEquals(new MessageResponse("This ingredient is already in your fridge"), responseEntity.getBody());
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//    }

    @Test
    public void ingredientDoesNotExistInFridge() {
        Ingredient ingredient = new Ingredient("skladnik", true);
        when(ingredientService.findById(ingredient.getId())).thenReturn(ingredient);
        when(fridgeService.hasIngredient(ingredient, user)).thenReturn(false);
        when(userService.findByUsername(authentication.getName())).thenReturn(user);

        ResponseEntity<?> responseEntity = fridgeController.addIngredient(authentication, ingredient.getId());
        verify(fridgeService, times(1)).addIngredient(ingredient, user);

        assertEquals(new MessageResponse("Ingredient successfully added to fridge"), responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

//    @Test
//    public void removeExistIngredient() {
//        Ingredient ingredient = new Ingredient("skladnik", true);
//        when(authentication.getName()).thenReturn(user.getUsername());
//        when(userService.findByUsername("User")).thenReturn(user);
//        when(ingredientService.findById(ingredient.getId())).thenReturn(ingredient);
//        when(fridgeService.hasIngredient(ingredient, user)).thenReturn(false);
//
//        ResponseEntity<?> responseEntity = fridgeController.removeIngredient(authentication, ingredient.getId());
//
//        verify(fridgeService, times(1)).deleteIngredient(ingredient, user);
//        assertEquals(new MessageResponse("Ingredient successfully removed from fridge"), responseEntity.getBody());
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

    @Test
    public void removeDoNotExistIngredient() {
        Ingredient ingredient = new Ingredient("skladnik", true);
        when(authentication.getName()).thenReturn(user.getUsername());
        when(userService.findByUsername("User")).thenReturn(user);
        when(ingredientService.findById(ingredient.getId())).thenReturn(ingredient);
        when(fridgeService.hasIngredient(ingredient, user)).thenReturn(false);
        when(userService.findByUsername(authentication.getName())).thenReturn(user);

        ResponseEntity<?> responseEntity = fridgeController.removeIngredient(authentication, ingredient.getId());

        assertEquals(new MessageResponse("Ingredient doesn't exist in fridge"), responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void clearFridge() {
        when(userService.findByUsername(authentication.getName())).thenReturn(user);
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(new MessageResponse("Fridge cleared successfully!"), HttpStatus.OK);
        ResponseEntity<?> responseEntity = fridgeController.clearFridge(authentication);
        assertEquals(expectedResponse, responseEntity);
    }
}