package pz.recipes.recipes.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pz.recipes.recipes.login.dto.LoginRequest;
import pz.recipes.recipes.login.service.LoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
