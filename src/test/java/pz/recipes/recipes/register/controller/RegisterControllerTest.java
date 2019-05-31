package pz.recipes.recipes.register.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.register.dto.RegisterRequest;
import pz.recipes.recipes.register.service.RegisterService;
import pz.recipes.recipes.users.service.UsersService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RegisterControllerTest {

    @InjectMocks
    RegisterController registerController = new RegisterController();
    @Mock
    RegisterService registerService;
    @Mock
    UsersService userService;

    @Test
    public void registerGetUsername() {
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        when(userService.ifUsernameExists(null)).thenReturn(true);
        ResponseEntity<?> responseEntity = registerController.register(registerRequest);
        assertEquals(new MessageResponse("This username already exists"), responseEntity.getBody());
        assertEquals(409,responseEntity.getStatusCodeValue());
    }
    @Test
    public void registerGetEmail() {
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        when(userService.ifEmailExists(null)).thenReturn(true);
        ResponseEntity<?> responseEntity = registerController.register(registerRequest);
        assertEquals(new MessageResponse("This email is already taken"), responseEntity.getBody());
        assertEquals(409,responseEntity.getStatusCodeValue());
    }
    @Test
    public void registerCheckPassword() {
        RegisterRequest registerRequest = mock(RegisterRequest.class);

        when(registerRequest.getPassword()).thenReturn("password");
        when(registerRequest.getConfirmPassword()).thenReturn("anotherPassword");
        ResponseEntity<?> responseEntity = registerController.register(registerRequest);
        assertEquals(new MessageResponse("Passwords are not the same"), responseEntity.getBody());
        assertEquals(409,responseEntity.getStatusCodeValue());
    }
    @Test
    public void registerCorrect() {
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        when(registerRequest.getPassword()).thenReturn("password");
        when(registerRequest.getConfirmPassword()).thenReturn("password");
        ResponseEntity<?> responseEntity = registerController.register(registerRequest);
        verify(registerService,times(1)).register(registerRequest);
        assertEquals(new MessageResponse("User successfully created"), responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }
}