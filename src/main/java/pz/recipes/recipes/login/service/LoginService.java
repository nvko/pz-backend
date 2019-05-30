package pz.recipes.recipes.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.login.dto.LoginRequest;
import pz.recipes.recipes.login.dto.LoginResponse;
import pz.recipes.recipes.repository.UsersRepository;
import pz.recipes.recipes.security.jwt.JwtTokenProvider;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = getAuthentication(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        User user = usersRepository.findByUsername(loginRequest.getUsername());
        return new ResponseEntity<>(new LoginResponse(user.getId(), jwt), HttpStatus.OK);
    }

    private Authentication getAuthentication(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
    }
}
