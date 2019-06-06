package pz.recipes.recipes.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.register.dto.RegisterRequest;
import pz.recipes.recipes.repository.UsersRepository;

import java.util.Collections;

@Service
public class RegisterService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest) {
        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                Collections.singletonList(Role.ROLE_USER),
                false, "https://team-recipes.herokuapp.com/images/avatars/default.jpg");
        userRepository.save(user);
    }

}
