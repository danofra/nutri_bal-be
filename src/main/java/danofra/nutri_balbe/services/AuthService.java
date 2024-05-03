package danofra.nutri_balbe.services;

import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.exceptions.UnauthorizedException;
import danofra.nutri_balbe.payloads.UserLoginDTO;
import danofra.nutri_balbe.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndToken(UserLoginDTO payload) {
        User user = this.userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }
}
