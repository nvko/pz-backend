package pz.recipes.recipes.users.controller;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pz.recipes.recipes.domain.Role;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.users.service.UsersService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.ok;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    UsersController userController;
    @Mock
    UsersService userService;

    @Test
    public void getUsers() {
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(Lists.emptyList(), HttpStatus.OK);
        ResponseEntity<?> responseEntity = userController.getUsers(10, 10, "id");
        verify(userService, times(1)).findAll(10, 10, "id");
        assertEquals(expectedResponse, responseEntity);
    }

    @Test
    public void getUser() {
        Long id = 13L;
        User user = new User("User", "email@interia.pl", "password", Collections.singletonList(Role.ROLE_ADMIN), true, "default.jpg");
        when(userService.findById(id)).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.getUser(id);
        assertEquals(responseEntity, ok(userService.findById(id)));
    }
}