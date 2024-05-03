package danofra.nutri_balbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import danofra.nutri_balbe.enums.Gender;
import danofra.nutri_balbe.enums.Physical_activity;
import danofra.nutri_balbe.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "role", "authorities", "accountNonExpired",
        "credentialsNonExpired", "accountNonLocked", "enabled"})
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate date_of_birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Physical_activity physical_activity;
    private String nationality;
    private String city_of_residence;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String avatar;

    public User(String name, String surname, String email, String password, LocalDate date_of_birth, Gender gender, Physical_activity physical_activity, String nationality, String city_of_residence) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.physical_activity = physical_activity;
        this.nationality = nationality;
        this.city_of_residence = city_of_residence;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
