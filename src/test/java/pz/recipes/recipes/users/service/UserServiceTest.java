package pz.recipes.recipes.users.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UsersService userService = new UsersService();
    @Mock
    UsersRepository userRepository;

    @Test
    public void findById() {
        User user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "default.jpg");
        Long id = 12L;
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        User byId = userService.findById(id);
        assertEquals(user,byId);
    }

    @Test
    public void findAll() {
        User user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "default.jpg");
        Page<User> pageUsers = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(PageRequest.of(10, 10, Sort.by("username")))).thenReturn(pageUsers);
        List<User> users = userService.findAll(10, 10, "username");
        assertEquals(pageUsers.getContent(),users);
    }

    @Test
    public void findByUsername() {
        User user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "default.jpg");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        User byId = userService.findByUsername(user.getUsername());
        assertEquals(user,byId);
    }

    @Test
    public void ifUsernameExists() {
        User user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "default.jpg");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        boolean isExist = userService.ifUsernameExists(user.getUsername());
        assertEquals(true,isExist);
    }
    @Test
    public void ifUsernameNotExists() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(null);
        boolean isExist = userService.ifUsernameExists(username);
        assertEquals(false,isExist);
    }

    @Test
    public void ifEmailExists() {
        User user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "default.jpg");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        boolean isExist = userService.ifEmailExists(user.getEmail());
        assertEquals(true,isExist);

    }

    @Test
    public void ifEmailNotExists() {
        String email = "email@interia.pl";
        when(userRepository.findByEmail(email)).thenReturn(null);
        boolean isExist = userService.ifEmailExists(email);
        assertEquals(false,isExist);
    }
}