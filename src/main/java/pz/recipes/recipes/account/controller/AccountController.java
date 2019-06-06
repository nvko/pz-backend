package pz.recipes.recipes.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.account.dto.AccountRequest;
import pz.recipes.recipes.account.service.AccountService;
import pz.recipes.recipes.domain.User;
import pz.recipes.recipes.users.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UsersService userService;

    // if user is vege/non-vege then if he calls this route then user's diet will switch to non-vege/vege
    @PutMapping("/diet")
    public ResponseEntity<?> updateDiet(Authentication authentication) {
        accountService.updateVege(authentication.getName());
        return new ResponseEntity<>(new MessageResponse("Your diet has been updated"), HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(Authentication authentication,
                                         @RequestBody AccountRequest accountRequest) {
        if (userService.ifEmailExists(accountRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("This email address is already taken"), HttpStatus.CONFLICT);
        } else {
            accountService.updateEmail(authentication.getName(), accountRequest.getEmail());
            return new ResponseEntity<>(new MessageResponse("Your email address has been updated"), HttpStatus.OK);
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(Authentication authentication,
                                            @RequestBody AccountRequest accountRequest) {
        if (!accountRequest.getPassword().equals(accountRequest.getConfirmPassword())) {
            return new ResponseEntity<>(new MessageResponse("Passwords are not the same"), HttpStatus.OK);
        }
        accountService.changePassword(authentication.getName(), accountRequest.getPassword());
        return new ResponseEntity<>(new MessageResponse("Your password has been changed"), HttpStatus.OK);
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> changeAvatar(Authentication authentication,
                                          @RequestBody AccountRequest accountRequest) {
//            String fileName = file.getOriginalFilename();
//            String finalFileName = authentication.getName() + fileName.substring(fileName.indexOf("."));
//            path = request.getServletContext().getRealPath("")+ "\\images\\avatars" + File.separator + finalFileName;
//            saveFile(file.getInputStream(), path);
        if (accountRequest.getAvatarPath() != null) {
            accountService.updateAvatar(authentication.getName(), accountRequest.getAvatarPath());
            return new ResponseEntity<>(new MessageResponse("Avatar updated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Error while updating avatar"), HttpStatus.BAD_REQUEST);
        }
    }

    private void saveFile(InputStream inputStream, String path) {
        try {
            OutputStream outputStream = new FileOutputStream((new File(path)));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
