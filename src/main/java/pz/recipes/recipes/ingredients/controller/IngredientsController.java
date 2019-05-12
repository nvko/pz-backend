package pz.recipes.recipes.ingredients.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.ingredients.dto.IngredientRequest;
import pz.recipes.recipes.ingredients.service.IngredientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientService ingredientService;

    // admin adds to ingredients, TODO: user adds to suggestions
    @PostMapping("")
    public ResponseEntity<?> addIngredient(Authentication authentication, @RequestBody IngredientRequest ingredientRequest) {
        if (authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            if (ingredientService.hasIngredient(ingredientRequest.getName())) {
                return new ResponseEntity<>(new MessageResponse("This ingredient already exists"), HttpStatus.CONFLICT);
            } else {
                ingredientService.addIngredient(ingredientRequest.getName(), ingredientRequest.getVege());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("You are not authorized to add new ingredients."), HttpStatus.UNAUTHORIZED);
        }
    }

    //TODO: create find controller for ingredients and recipes
//    @GetMapping("/{query}")
//    public ResponseEntity<?> getByQuery(@PathVariable(name = "query") String query) {
//        return new ResponseEntity<>(new IngredientResponse(ingredientService.findByQuery(query)), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(ingredientService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(Authentication authentication, @PathVariable(name = "id") Long id) {
        if (authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            if (ingredientService.hasIngredient(id)) {
                ingredientService.deleteIngredient(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("This ingredient doesn't exist"), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("You are not authorized to delete new ingredients."), HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateIngredient(Authentication authentication, @PathVariable(name = "id") Long id, @Valid @RequestBody IngredientRequest ingredientRequest) {
        if (authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            if (ingredientService.hasIngredient(id)) {
                ingredientService.updateIngredient(id, ingredientRequest);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("This ingredient doesn't exist"), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("You are not authorized to update ingredients."), HttpStatus.UNAUTHORIZED);
        }
    }
}
