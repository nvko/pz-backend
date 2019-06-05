package pz.recipes.recipes.account.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.repository.UsersRepository;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService = new AccountService();
    @Mock
    UsersRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    User user;

    @Before
    public void setUp() {
        user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_ADMIN), true, "default.jpg");
    }

    @Test
    public void updateVege() {
        Boolean expectedVege = !user.getVege();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        accountService.updateVege(user.getUsername());
        assertEquals(expectedVege, user.getVege());
    }

    @Test
    public void updateEmail() {
        String newEmail = "newEmail@iinteria.pl";
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        accountService.updateEmail(user.getUsername(),newEmail);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void changePassword() {
        String newPassword = "newPassword";
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
        accountService.changePassword(user.getUsername(),newPassword);
        assertEquals(newPassword, user.getPassword());
    }
}