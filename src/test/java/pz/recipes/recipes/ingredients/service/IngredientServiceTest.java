package pz.recipes.recipes.ingredients.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pz.recipes.recipes.domain.Ingredient;
import pz.recipes.recipes.ingredients.dto.IngredientsRequest;
import pz.recipes.recipes.repository.IngredientsRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class IngredientServiceTest {

    @InjectMocks
    IngredientsService ingredientService = new IngredientsService();
    @Mock
    IngredientsRepository ingredientRepository;

    @Test
    public void addIngredient() {
        Ingredient ingredient = new Ingredient("ingredient", true);
        ingredientService.addIngredient(ingredient.getName(),ingredient.getVege());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    public void findById() {
        Ingredient ingredient = spy(new Ingredient("ingredient", true));
        Long id = 12L;
        when(ingredientRepository.findById(id)).thenReturn(Optional.ofNullable(ingredient));
        Ingredient byId = ingredientService.findById(id);
        assertEquals(ingredient, byId);
    }

    @Test
    public void hasIngredientId() {
        Ingredient ingredient = spy(new Ingredient("ingredient", true));
        Long id = 12L;
        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        Ingredient byId = ingredientService.findById(id);
        assertEquals(ingredient,byId);
    }

    @Test
    public void hasIngredientName() {
        String name = "ingredient";
        Ingredient ingredient = new Ingredient("ingredient", true);
        when(ingredientRepository.findByName(name)).thenReturn(Collections.singletonList(ingredient));
        boolean exist = ingredientService.hasIngredient(name);
        assertEquals(true,exist);
    }

    @Test
    public void deleteIngredient() {
        Ingredient ingredient = spy(new Ingredient("ingredient", true));
        Long id = 12L;
        when(ingredientRepository.findById(id)).thenReturn(Optional.ofNullable(ingredient));
        ingredientService.deleteIngredient(id);
        verify(ingredientRepository,times(1)).delete(ingredient);
    }

    @Test
    public void updateIngredient() {
        IngredientsRequest ingredientRequest = spy(new IngredientsRequest());
        Ingredient ingredient = spy(new Ingredient("ingredient", true));
        Long id = 12L;
        when(ingredientRepository.findById(id)).thenReturn(Optional.ofNullable(ingredient));
        when(ingredientRequest.getName()).thenReturn("ingredientName");
        when(ingredientRequest.getVege()).thenReturn(false);
        ingredientService.updateIngredient(id,ingredientRequest);
        verify(ingredient,times(1)).setName("ingredientName");
        verify(ingredient,times(1)).setVege(false);
        verify(ingredientRepository,times(1)).save(ingredient);

    }
}