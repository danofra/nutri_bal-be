package danofra.nutri_balbe.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import danofra.nutri_balbe.entities.User;
import danofra.nutri_balbe.enums.Role;
import danofra.nutri_balbe.exceptions.BadRequestException;
import danofra.nutri_balbe.exceptions.NotFoundException;
import danofra.nutri_balbe.payloads.UserDTO;
import danofra.nutri_balbe.payloads.UserResponseDTO;
import danofra.nutri_balbe.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary cloudinary;

    public UserResponseDTO save(UserDTO newUser) throws BadRequestException {
        Optional<User> existingUser = this.userDAO.findByEmail(newUser.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("Email " + newUser.email() + " exists!");
        }
        User user = new User();
        user.setName(newUser.name());
        user.setSurname(newUser.surname());
        user.setEmail(newUser.email());
        user.setPassword(bcrypt.encode(newUser.password()));
        user.setDate_of_birth(newUser.date_of_birth());
        user.setGender(newUser.gender());
        user.setPhysical_activity(newUser.physical_activity());
        user.setNationality(newUser.nationality());
        user.setCity_of_residence(newUser.city_of_residence());
        user.setRole(Role.USER);
        user.setAvatar("https://ui-avatars.com/api/?name=" + newUser.name() + "+" + newUser.surname());
        userDAO.save(user);
        return new UserResponseDTO(user.getEmail());
    }

    public Page<User> getUser(int page, int size, String sortBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userDAO.findAll(pageable);
    }

    public User findById(int id) {
        return this.userDAO.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public User findByEmail(String email) {
        return this.userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));
    }

    public void findByIdAndDelete(int id) {
        User user = this.findById(id);
        this.userDAO.delete(user);
    }

    public User findByIdAndUpdate(int id, UserDTO newUser) {
        User user = this.findById(id);
        user.setName(newUser.name());
        user.setSurname(newUser.surname());
        user.setEmail(newUser.email());
        user.setPassword(bcrypt.encode(newUser.password()));
        user.setDate_of_birth(newUser.date_of_birth());
        user.setGender(newUser.gender());
        user.setPhysical_activity(newUser.physical_activity());
        user.setNationality(newUser.nationality());
        user.setCity_of_residence(newUser.city_of_residence());
        user.setAvatar("https://ui-avatars.com/api/?name=" + newUser.name() + "+" + newUser.surname());
        return this.userDAO.save(user);
    }

    public String upload(MultipartFile image) throws IOException {
        String url = (String) cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }

    public User uploadUserImageToId(MultipartFile image, int userId) throws IOException {
        User user = this.findById(userId);
        user.setAvatar(this.upload(image));
        this.userDAO.save(user);
        return user;
    }
}
