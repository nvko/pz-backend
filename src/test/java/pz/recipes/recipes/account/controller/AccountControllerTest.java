package pz.recipes.recipes.account.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.account.dto.AccountRequest;
import pz.recipes.recipes.account.service.AccountService;
import pz.recipes.recipes.users.service.UsersService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountControllerTest {

    @InjectMocks
    AccountController accountController = new AccountController();

    @Mock
    AccountService accountService;
    @Mock
    UsersService userService;

    @Test
    public void updateDiet() {
        String user = "User";

        Authentication mockedAuthentication = mock(Authentication.class);
        when(mockedAuthentication.getName()).thenReturn(user);

        ResponseEntity<?> responseEntity = accountController.updateDiet(mockedAuthentication);

        verify(mockedAuthentication,times(1)).getName();
        verify(accountService,times(1)).updateVege(user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new MessageResponse("Your diet has been updated"),responseEntity.getBody());
    }

    @Test
    public void updateEmailIfEmailExists() {
        String email = "email@interia.pl";
        AccountRequest spyAccountRequest = spy(new AccountRequest(email));
        Authentication mockedAuthentication = mock(Authentication.class);

        when(userService.ifEmailExists(email)).thenReturn(true);
        ResponseEntity<?> responseEntity = accountController.updateEmail(mockedAuthentication, spyAccountRequest);

        verify(userService, times(1)).ifEmailExists(email);
        verify(spyAccountRequest, times(1)).getEmail();

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(new MessageResponse("This email address is already taken"),responseEntity.getBody());
    }


    @Test
    public void updateEmailIfEmailNotExists() {
        String email = "email@interia.pl";
        AccountRequest spyAccountRequest = spy(new AccountRequest(email));
        Authentication mockedAuthentication = mock(Authentication.class);

        when(userService.ifEmailExists(email)).thenReturn(false);
        ResponseEntity<?> responseEntity = accountController.updateEmail(mockedAuthentication, spyAccountRequest);

        verify(userService, times(1)).ifEmailExists(email);
        verify(spyAccountRequest, times(2)).getEmail();
        verify(mockedAuthentication,times(1)).getName();
        verify(accountService,times(1)).updateEmail(mockedAuthentication.getName(),spyAccountRequest.getEmail());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new MessageResponse("Your email address has been updated"),responseEntity.getBody());
    }

    @Test
    public void checkDifferentPasswords() {
        AccountRequest accountRequest = spy(new AccountRequest("password","otherPassword"));
        Authentication mockedAuthentication = mock(Authentication.class);

        ResponseEntity<?> responseEntity = accountController.changePassword(mockedAuthentication, accountRequest);

        verify(accountRequest,times(1)).getPassword();
        verify(accountRequest,times(1)).getConfirmPassword();

        assertEquals(new MessageResponse("Passwords are not the same"),responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    public void checkSamePasswords() {
        AccountRequest accountRequest = spy(new AccountRequest("password","password"));
        Authentication mockedAuthentication = mock(Authentication.class);

        ResponseEntity<?> responseEntity = accountController.changePassword(mockedAuthentication, accountRequest);
        verify(accountRequest,times(2)).getPassword();
        verify(accountRequest,times(1)).getConfirmPassword();
        verify(mockedAuthentication,times(1)).getName();
        assertEquals(new MessageResponse("Your password has been changed"),responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}