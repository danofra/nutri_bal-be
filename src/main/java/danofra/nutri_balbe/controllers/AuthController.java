package danofra.nutri_balbe.controllers;

import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.payloads.UserDTO;
import danofra.nutri_balbe.payloads.UserLoginDTO;
import danofra.nutri_balbe.payloads.UserLoginResponseDTO;
import danofra.nutri_balbe.payloads.UserResponseDTO;
import danofra.nutri_balbe.services.AuthService;
import danofra.nutri_balbe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody @Validated UserLoginDTO payload, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        return new UserLoginResponseDTO(this.authService.authenticateUserAndToken(payload));
    }

    @PostMapping("/register")
    public UserResponseDTO savaUser(@RequestBody @Validated UserDTO body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        return new UserResponseDTO(this.userService.save(body).email());
    }
}
