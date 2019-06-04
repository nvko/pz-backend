package pz.recipes.recipes.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.recipes.dto.RecipesResponse;
import pz.recipes.recipes.users.service.UsersService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private UsersService userService;

    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                      @RequestParam(value = "sort", required = false, defaultValue = "id") String sort) {
        return new ResponseEntity<>(userService.findAll(page, limit, sort), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ok(userService.findById(id));
    }


    @GetMapping(path = "/{id}/recipes")
    public ResponseEntity<?> getUsersRecipes(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                             @PathVariable Long id) {
        return new ResponseEntity<>(new RecipesResponse(userService.findAllByUser(id, page, limit)), HttpStatus.OK);
    }

}