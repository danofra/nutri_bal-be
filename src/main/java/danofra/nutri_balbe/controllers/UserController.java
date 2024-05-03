package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.payloads.UserDTO;
import danofra.nutri_balbe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUser(page, size, sortBy);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByEmail(@PathVariable String email) {
        return this.userService.findByEmail(email);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByEmailAndDelete(@PathVariable String email) {
        userService.findByEmailAndDelete(email);
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image) throws IOException {
        return this.userService.upload(image);
    }

    @PostMapping("/upload/{userId}")
    public String uploadUserImage(@RequestParam("avatar") MultipartFile image, @PathVariable int userId) throws IOException {
        this.userService.uploadUserImageToId(image, userId);
        return this.userService.upload(image);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody UserDTO updatedUser) {
        return this.userService.findByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.findByEmailAndDelete(currentAuthenticatedUser.getEmail());
    }
}
