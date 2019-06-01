package pz.recipes.recipes.fridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.fridge.dto.FridgeResponse;
import pz.recipes.recipes.fridge.service.FridgeService;
import pz.recipes.recipes.ingredients.service.IngredientsService;
import pz.recipes.recipes.users.service.UsersService;

@RestController
@RequestMapping("/fridge")
public class FridgeController {

    @Autowired FridgeService fridgeService;
    @Autowired UsersService userService;
    @Autowired IngredientsService ingredientService;

    @GetMapping("")
    public ResponseEntity<?> getFridge(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return new ResponseEntity<>(new FridgeResponse(fridgeService.findByUser(user)), HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addIngredient(Authentication authentication, @PathVariable(name = "id") Long ingredientId) {
        User user = userService.findByUsername(authentication.getName());
        if (fridgeService.hasIngredient(ingredientService.findById(ingredientId), user)) {
            return new ResponseEntity<>(new MessageResponse("This ingredient is already in your fridge"), HttpStatus.BAD_REQUEST);
        } else {
            fridgeService.addIngredient(ingredientService.findById(ingredientId), user);
            return new ResponseEntity<>(new MessageResponse("Ingredient successfully added to fridge"), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeIngredient(Authentication authentication, @PathVariable(name = "id") Long ingredientId) {
        User user = userService.findByUsername(authentication.getName());
        if (!fridgeService.hasIngredient(ingredientService.findById(ingredientId), user)) {
            return new ResponseEntity<>(new MessageResponse("Ingredient doesn't exist in fridge"), HttpStatus.BAD_REQUEST);
        } else {
            fridgeService.deleteIngredient(ingredientService.findById(ingredientId), user);
            return new ResponseEntity<>(new MessageResponse("Ingredient successfully removed from fridge"), HttpStatus.OK);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearFridge(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        fridgeService.clearFridge(user);
        return new ResponseEntity<>(new MessageResponse("Fridge cleared successfully!"), HttpStatus.OK);
    }


}
