package pz.recipes.recipes.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.register.dto.RegisterRequest;
import pz.recipes.recipes.register.service.RegisterService;
import pz.recipes.recipes.users.service.UsersService;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private UsersService userService;

    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest.getUsername().equals("default")) {
            return ResponseEntity.status(409).body(new MessageResponse("This username is forbidden."));
        } else if (userService.ifUsernameExists(registerRequest.getUsername())) {
            return ResponseEntity.status(409).body(new MessageResponse("This username already exists"));
        } else if (userService.ifEmailExists(registerRequest.getEmail())) {
            return ResponseEntity.status(409).body(new MessageResponse("This email is already taken"));
        } else if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.status(409).body(new MessageResponse("Passwords are not the same"));
        } else {
            registerService.register(registerRequest);
            return ResponseEntity.ok(new MessageResponse("User successfully created"));
        }
    }


}
