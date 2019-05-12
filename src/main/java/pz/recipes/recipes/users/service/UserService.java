package pz.recipes.recipes.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll(int page, int limit, String sort) {
        return userRepository.findAll(PageRequest.of(page, limit, Sort.by(sort))).getContent();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean ifUsernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public boolean ifEmailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

}
