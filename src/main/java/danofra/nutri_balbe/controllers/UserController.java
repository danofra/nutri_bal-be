package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.UserDTO;
import danofra.nutri_balbe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO user(@RequestBody @Validated UserDTO body, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setDate_of_birth(body.date_of_birth());
        user.setGender(body.gender());
        user.setPhysical_activity(body.physical_activity());
        user.setNationality(body.nationality());
        user.setCity_of_residence(body.city_of_residence());
        user.setRole(body.role());
        user.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        userService.save(body);
        return body;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUser(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable int userId) {
        return this.userService.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findAndUpdate(@PathVariable int userId, @RequestBody UserDTO body) {
        return userService.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findAndDelete(@PathVariable int userId) {
        userService.findByIdAndDelete(userId);
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
        this.userService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }
}
