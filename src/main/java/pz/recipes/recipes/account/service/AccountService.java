package pz.recipes.recipes.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.UserRepository;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updateVege(String username) {
        User user = userRepository.findByUsername(username);
        Boolean vege = user.getVege();
        user.setVege(!vege);
        userRepository.save(user);
    }

    public void updateEmail(String username, String email) {
        User user = userRepository.findByUsername(username);
        user.setEmail(email);
        userRepository.save(user);
    }


    public void changePassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
