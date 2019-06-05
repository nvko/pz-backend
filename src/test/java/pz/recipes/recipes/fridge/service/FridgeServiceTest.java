package pz.recipes.recipes.fridge.service;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pz.recipes.recipes.domain.Fridge;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.FridgeRepository;
import pz.recipes.recipes.repository.IngredientsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FridgeServiceTest {

    @InjectMocks
    FridgeService fridgeService = new FridgeService();
    @Mock
    FridgeRepository fridgeRepository;
    @Mock
    IngredientsRepository ingredientRepository;

    User user;

    @Before
    public void setUp() {
        user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), true, "default.jpg");
    }

    @Test
    public void findByUser() {
        List<Fridge> byUser = fridgeService.findByUser(user);
        assertEquals(Lists.emptyList(), byUser);
    }

    @Test
    public void addIngredient() {
        Ingredient ingredient = spy(new Ingredient("skladnik", true));
        Fridge fridge = new Fridge(user, ingredient);
        when(ingredient.getId()).thenReturn(12L);
        when(ingredientRepository.findById(ingredient.getId())).thenReturn(Optional.of(ingredient));
        fridgeService.addIngredient(ingredient, user);
        Mockito.verify(fridgeRepository, times(1)).save(fridge);
    }

    @Test
    public void deleteIngredient() {
        Ingredient ingredient = new Ingredient("skladnik", true);
        Fridge fridge = new Fridge(user, ingredient);
        when(fridgeRepository.findByUserAndIngredient(user, ingredient)).thenReturn(fridge);
        fridgeService.deleteIngredient(ingredient, user);
        Mockito.verify(fridgeRepository, times(1)).delete(fridge);
    }

    @Test
    public void hasIngredientExist() {
        Ingredient ingredient = new Ingredient("skladnik", true);
        Fridge fridge = spy(new Fridge(user, ingredient));
        when(fridgeRepository.findByUserAndIngredient(user, ingredient)).thenReturn(fridge);
        boolean exist = fridgeService.hasIngredient(ingredient, user);
        assertEquals(true, exist);
    }

    @Test
    public void hasIngredientNotExist() {
        Ingredient ingredient = new Ingredient("skladnik", true);
        when(fridgeRepository.findByUserAndIngredient(user, ingredient)).thenReturn(null);
        boolean exist = fridgeService.hasIngredient(ingredient, user);
        assertEquals(false, exist);
    }

    @Test
    public void clearFridge() {
        fridgeService.clearFridge(user);
        Mockito.verify(fridgeRepository, times(1)).deleteAllByUser(user);
    }
}