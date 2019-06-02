package pz.recipes.recipes.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pz.recipes.recipes.domain.Recipe;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.RecipesRepository;
import pz.recipes.recipes.repository.UsersRepository;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired private RecipesRepository recipesRepository;

    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<User> findAll(int page, int limit, String sort) {
        return usersRepository.findAll(PageRequest.of(page, limit, Sort.by(sort))).getContent();
    }

    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public boolean ifUsernameExists(String username) {
        User user = usersRepository.findByUsername(username);
        return user != null;
    }

    public boolean ifEmailExists(String email) {
        User user = usersRepository.findByEmail(email);
        return user != null;
    }

    public List<Recipe> findAllByUser(Long id, int page, int limit) {
        User user = usersRepository.findById(id).get();
        return recipesRepository.findAllByUser(user, PageRequest.of(page, limit));
    }

}
