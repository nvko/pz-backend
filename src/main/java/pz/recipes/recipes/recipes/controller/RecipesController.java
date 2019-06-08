package pz.recipes.recipes.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.recipes.dto.RecipesRequest;
import pz.recipes.recipes.recipes.dto.RecipesResponse;
import pz.recipes.recipes.recipes.service.RecipesService;
import pz.recipes.recipes.users.service.UsersService;

@RequestMapping("recipes")
@RestController
public class RecipesController {

    @Autowired RecipesService recipeService;
    @Autowired UsersService userService;

    @GetMapping("")
    public ResponseEntity<?> getRecipes(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                        @RequestParam(value = "sort", defaultValue = "id") String sort) {
        return new ResponseEntity<>(new RecipesResponse(recipeService.getRecipes(page, limit, sort)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable("id") Long id) {
        return new ResponseEntity<>(recipeService.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity addRecipe(Authentication authentication, @RequestBody RecipesRequest recipeRequest) {
        if (recipeRequest.hasSomethingNull()) {
            return new ResponseEntity<>(new MessageResponse("Request is invalid"), HttpStatus.CONFLICT);
        } else {
            User user = userService.findByUsername(authentication.getName());
            if (user != null) {
                recipeService.addRecipe(user, recipeRequest);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(Authentication authentication, @PathVariable(name = "id") Long id, @RequestBody RecipesRequest recipeRequest) {
        if (recipeRequest.hasSomethingNull()) {
            return new ResponseEntity<>(new MessageResponse("Request is invalid"), HttpStatus.CONFLICT);
        } else {
            User user = userService.findByUsername(authentication.getName());
            if (user.getId().equals(recipeService.findById(id).getUser().getId()) || user.getRoles().contains(Role.ROLE_ADMIN)) {
                recipeService.updateRecipe(recipeRequest, id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(Authentication authentication, @PathVariable(name = "id") Long id) {
        User user = userService.findByUsername(authentication.getName());
        if (user.getId().equals(recipeService.findById(id).getUser().getId()) || user.getRoles().contains(Role.ROLE_ADMIN)) {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
