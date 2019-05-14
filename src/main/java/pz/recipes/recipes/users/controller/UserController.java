package pz.recipes.recipes.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.users.service.UserService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                      @RequestParam(value = "sort", required = false, defaultValue = "id") String sort) {
        return new ResponseEntity<>(userService.findAll(page, limit, sort), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<pz.recipes.recipes.domain.User> getUser(@PathVariable Long id) {
        return ok(userService.findById(id));
    }


}