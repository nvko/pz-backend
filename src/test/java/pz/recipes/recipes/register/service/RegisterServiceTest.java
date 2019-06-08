package pz.recipes.recipes.register.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.register.dto.RegisterRequest;
import pz.recipes.recipes.repository.UsersRepository;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RegisterServiceTest {

    @InjectMocks
    RegisterService registerService = new RegisterService();
    @Mock
    UsersRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    //TODO: fix
    @Test
    public void register() {
        User user = new User("User","email@interia.pl", "password", Collections.singletonList(Role.ROLE_USER), false, "https://team-recipes.herokuapp.com/images/avatars/default.jpg");
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        when(registerRequest.getUsername()).thenReturn(user.getUsername());
        when(registerRequest.getEmail()).thenReturn(user.getEmail());
        when(registerRequest.getPassword()).thenReturn(user.getPassword());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn(user.getPassword());
        registerService.register(registerRequest);
        verify(userRepository,times(1)).save(user);
    }
}